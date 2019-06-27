package com.medulasales.products.services.impl;

import com.medulasales.products.entities.Category;
import com.medulasales.products.entities.Product;
import com.medulasales.products.exceptions.BusinessException;
import com.medulasales.products.properties.ServiceProperties;
import com.medulasales.products.providers.UtilityProvider;
import com.medulasales.products.repositories.CategoryRepository;
import com.medulasales.products.repositories.ProductCategoryXRefRepository;
import com.medulasales.products.repositories.ProductRepository;
import com.medulasales.products.services.CategoryService;
import com.medulasales.products.services.MediaService;
import com.medulasales.products.services.ProductService;
import com.medulasales.products.services.dto.category.CategoryFullDto;
import com.medulasales.products.services.dto.product.ProductFullDto;
import com.medulasales.products.services.dto.product.ProductToCreateDto;
import com.medulasales.products.services.dto.product.ProductToPatchDto;
import com.medulasales.products.services.dto.product.ProductToUpdateDto;
import com.medulasales.products.services.mapping.ProductMapper;
import com.medulasales.products.utils.uniql.Direction;
import com.medulasales.products.utils.uniql.Uniql;
import io.github.perplexhub.rsql.RSQLSupport;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.javatuples.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
@Transactional
@Validated
public class ProductServiceImpl implements ProductService {

    private ServiceProperties serviceProperties;
    private MediaService mediaService;
    private CategoryService categoryService;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private ProductCategoryXRefRepository productCategoryXRefRepository;
    private UtilityProvider utilityProvider;
    private ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(
      ServiceProperties serviceProperties,
      ProductRepository productRepository,
      CategoryRepository categoryRepository,
      ProductCategoryXRefRepository productCategoryXRefRepository,
      UtilityProvider utilityProvider,
      ProductMapper productMapper,
      MediaService mediaService,
      CategoryService categoryService
      ) {
        this.serviceProperties = serviceProperties;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productCategoryXRefRepository = productCategoryXRefRepository;
        this.utilityProvider = utilityProvider;
        this.productMapper = productMapper;
        this.mediaService = mediaService;
    }

    private void existsByUuidOrThrow(String uuid) throws BusinessException {
        if(!productRepository.existsByUuid(uuid)) {
            throw BusinessException.PRODUCT_NOT_FOUND_BY_UUID(uuid);
        }
    }

    private Triplet<Specification<Product>, Pageable, Sort> extractFilterSpecifications(@NotNull Uniql filter) {
        Specification<Product> specs = null;
        Pageable pageable = null;
        Sort sort = null;
        if(filter.hasQuery()) {
            specs = RSQLSupport.rsql(filter.getQuery());
        }
        if(filter.hasSort()) {
            Sort.Direction dir = filter.getSort().getDirection() == Direction.DESC ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(dir, filter.getSort().getFieldNames());
        }
        if(filter.hasPage()) {
            pageable = PageRequest.of(filter.getPage().getNumber(), filter.getPage().getSize(), sort);
        }
        return Triplet.with(specs, pageable, sort);
    }

    @Override
    public ProductFullDto createProduct(@Valid ProductToCreateDto productToCreateDto) throws BusinessException {
        if(productRepository.existsByName(productToCreateDto.getName())) {
            throw BusinessException.PRODUCT_NAME_ALREADY_EXIST(productToCreateDto.getName());
        }
        Product product = productMapper.Product_From_ProductToCreateDto(productToCreateDto);
        productRepository.save(product);
        return productMapper.ProductFullDto_From_Product(product);
    }

    @Override
    public List<ProductFullDto> createAllProducts(List<@Valid ProductToCreateDto> productToCreateDtos) throws BusinessException {
        List<ProductFullDto> productFullDtos = new ArrayList<>();
        for (ProductToCreateDto productToCreateDto : productToCreateDtos) {
            productFullDtos.add(createProduct(productToCreateDto));
        }
        return productFullDtos;
    }

    @Override
    public ProductFullDto updateProduct(@Valid ProductToUpdateDto patchDto) throws BusinessException {
        existsByUuidOrThrow(patchDto.getUuid());
        Product product = productRepository.findByUuid(patchDto.getUuid()).get();
        if(!patchDto.getName().equals(product.getName()) && productRepository.existsByName(patchDto.getName())) {
            throw BusinessException.PRODUCT_NAME_ALREADY_EXIST(patchDto.getName());
        }
        productMapper.updateProduct_From_ProductToUpdateDto(patchDto, product);
        productRepository.save(product);
        return productMapper.ProductFullDto_From_Product(product);
    }

    @Override
    public List<ProductFullDto> updateAllProducts(List<@Valid ProductToUpdateDto> productToUpdateDtos) throws BusinessException {
        List<ProductFullDto> productFullDtos = new ArrayList<>();
        for (ProductToUpdateDto productToUpdateDto : productToUpdateDtos) {
            productFullDtos.add(updateProduct(productToUpdateDto));
        }
        return productFullDtos;
    }

    @Override
    public ProductFullDto patchProduct(@Valid ProductToPatchDto patchDto) throws BusinessException {
        existsByUuidOrThrow(patchDto.getUuid());
        Product product = productRepository.findByUuid(patchDto.getUuid()).get();
        ProductToUpdateDto updateDto = productMapper.ProductToUpdate_From_ProductToPatchDto_And_Product(patchDto, product);
        return updateProduct(updateDto);
    }

    @Override
    public List<ProductFullDto> patchAllProducts(List<@Valid ProductToPatchDto> productsToPatchDtos) throws BusinessException {
        List<ProductFullDto> productFullDtos = new ArrayList<>();
        for (ProductToPatchDto productToPatchDto : productsToPatchDtos) {
            productFullDtos.add(patchProduct(productToPatchDto));
        }
        return productFullDtos;
    }

    @Override
    public void deleteProduct(String uuid) throws BusinessException {
        Optional<Product> productOptional = productRepository.findByUuid(uuid);
        if(!productOptional.isPresent()) {
            throw BusinessException.PRODUCT_NOT_FOUND_BY_UUID(uuid);
        }
        productRepository.delete(productOptional.get());
    }

    @Override
    public void deleteAllProducts(@NotNull List<String> uuids) throws BusinessException {
        for(String uuid : uuids) {
            deleteProduct(uuid);
        }
    }

    @Override
    public ProductFullDto findProduct(@NotNull String uuid, Uniql filter) throws BusinessException {
        existsByUuidOrThrow(uuid);
        Product product = productRepository.findByUuid(uuid).get();
        return productMapper.ProductFullDto_From_Product(product, filter);
    }

    @Override
    public ProductFullDto findProduct(@NotNull String uuid) throws BusinessException {
        return findProduct(uuid, null);
    }

    @Override
    public ProductFullDto findProductByName(@NotNull String name, Uniql filter) throws BusinessException {
        if(!productRepository.existsByName(name)) {
            throw BusinessException.PRODUCT_NOT_FOUND_BY_NAME(name);
        }
        return productMapper.ProductFullDto_From_Product(productRepository.findByName(name).get(), filter);
    }

    @Override
    public ProductFullDto findProductByName(@NotNull String name) throws BusinessException {
        return findProductByName(name, null);
    }

    @Override
    public List<ProductFullDto> findAllProducts(Uniql filter) throws BusinessException {
        long count = productRepository.count();
        if(count > serviceProperties.getMaxPageSize()) {
            throw BusinessException.RESULT_COUNT_TOO_LONG(serviceProperties.getMaxPageSize());
        }
        if(filter == null) {
            return productMapper.ProductFullDtos_From_Products(productRepository.findAll());
        }
        Triplet<Specification<Product>, Pageable, Sort> specs = extractFilterSpecifications(filter);
        return productMapper.ProductFullDtos_From_Products(productRepository.findAll(specs.getValue0(), specs.getValue2()), filter);
    }

    @Override
    public List<ProductFullDto> findAllProducts() throws BusinessException {
        return findAllProducts(null);
    }

    @Override
    public Page<ProductFullDto> findProductsPage(Uniql filter) throws BusinessException {
        if(filter == null) {
            Page<Product> products = productRepository.findAll(PageRequest.of(serviceProperties.getDefaultPageNumber(), serviceProperties.getDefaultPageSize()));
            return productMapper.ProductFullDtos_From_Products(products);
        }
        Triplet<Specification<Product>, Pageable, Sort> specs = extractFilterSpecifications(filter);
        Pageable pageable = specs.getValue1();
        if(pageable.getPageSize() > serviceProperties.getMaxPageSize()) {
            pageable = PageRequest.of(pageable.getPageNumber(), serviceProperties.getMaxPageSize(), pageable.getSort());
        }
        return productMapper.ProductFullDtos_From_Products(productRepository.findAll(specs.getValue0(), pageable));
    }

    @Override
    public List<ProductFullDto> findAllProductsByCategory(@NotNull String categoryUuid, Uniql filter) throws BusinessException {
        if(!categoryRepository.existsByUuid(categoryUuid)) {
            throw BusinessException.CATEGORY_NOT_FOUND_BY_UUID(categoryUuid);
        }
        long count = productCategoryXRefRepository.countAllByCategoryUuid(categoryUuid);
        if(count > serviceProperties.getMaxPageSize()) {
            throw BusinessException.RESULT_COUNT_TOO_LONG(serviceProperties.getMaxPageSize());
        }
        return null;
    }

    @Override
    public List<ProductFullDto> findAllProductsByCategory(@NotNull String categoryUuid) throws BusinessException {
        return null;
    }

    @Override
    public Page<ProductFullDto> findProductsPageByCategory(@NotNull String categoryUuid, Uniql filter) throws BusinessException {
        return null;
    }

    @Override
    public ProductFullDto setProductPicture(@NotNull String productUuid, MultipartFile pictureFile) throws BusinessException {
        return null;
    }

    @Override
    public ProductFullDto removeProductPicture(@NotNull String productUuid) throws BusinessException {
        return null;
    }
}
