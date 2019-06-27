package com.medulasales.products.repositories.extensions.impl;

import com.medulasales.products.entities.*;
import com.medulasales.products.repositories.CategoryRepository;
import com.medulasales.products.repositories.ProductCategoryXRefRepository;
import com.medulasales.products.repositories.ProductRepository;
import com.medulasales.products.repositories.extensions.ProductCategoryXRefRepositoryExtension;
import com.medulasales.products.utils.jmgenericxrefqueryretriever.JmXRefException;
import com.medulasales.products.utils.jmgenericxrefqueryretriever.JmXRefRetriever;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductCategoryXRefRepositoryExtensionImpl extends BaseRepositoryExtension implements ProductCategoryXRefRepositoryExtension {

    private ProductCategoryXRefRepository repository;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    void contextLoad() {
        repository = getBean(ProductCategoryXRefRepository.class);
        productRepository = getBean(ProductRepository.class);
        categoryRepository = getBean(CategoryRepository.class);
    }

    private JmXRefRetriever<Product, ProductCategoryXRef> createProductXRefRetriever(String categoryUuid, Specification<Product> spec, Pageable pageable) {
        Category category = categoryRepository.findByUuid(categoryUuid).get();
        return createXRefRetriever(Product.class, ProductCategoryXRef.class, Category_.ID, category.getId(), spec, pageable);
    }

    private JmXRefRetriever<Category, ProductCategoryXRef> createCategoryXRefRetriever(String productUuid, Specification<Category> spec, Pageable pageable) {
        Product product = productRepository.findByUuid(productUuid).get();
        return createXRefRetriever(Category.class, ProductCategoryXRef.class, Product_.ID, product.getId(), spec, pageable);
    }

    @Override
    public ProductCategoryXRef addProductIntoCategory(String productUuid, String categoryUuid) throws JmXRefException {
        Product product = productRepository.findByUuid(productUuid).get();
        Category category = categoryRepository.findByUuid(categoryUuid).get();
        ProductCategoryXRef xref = ProductCategoryXRef.builder()
          .product(product)
          .category(category)
          .build();
        repository.save(xref);
        return xref;
    }

    @Override
    public void removeProductFromCategory(String productUuid, String categoryUuid) throws JmXRefException {
        Optional<ProductCategoryXRef> xrefOpt = repository.findByProductUuidAndCategoryUuid(productUuid, categoryUuid);
        xrefOpt.ifPresent(xref -> {
            repository.delete(xref);
        });
    }

    @Override
    public List<Product> findAllProductsIntoCategory
      (String categoryUuid) throws JmXRefException {
        return createProductXRefRetriever(categoryUuid, null, null).retrieveList(Product.class);
    }

    @Override
    public Page<Product> findAllProductsIntoCategory
      (String categoryUuid, Pageable page) throws JmXRefException {
        return createProductXRefRetriever(categoryUuid, null, page).retrievePage(Product.class);
    }

    @Override
    public List<Product> findAllProductsIntoCategory
      (String categoryUuid, Specification<Product> productSpec) throws JmXRefException {
        return createProductXRefRetriever(categoryUuid, productSpec, null).retrieveList(Product.class);
    }

    @Override
    public Page<Product> findAllProductsIntoCategory
      (String categoryUuid, Specification<Product> productSpec, Pageable pageable) throws JmXRefException {
        return createProductXRefRetriever(categoryUuid, productSpec, pageable).retrievePage(Product.class);
    }

    @Override
    public List<Category> findAllCategoriesOfProduct(String productUuid) throws JmXRefException {
        return createCategoryXRefRetriever(productUuid, null, null).retrieveList(Category.class);
    }

    @Override
    public Page<Category> findAllCategoriesOfProduct
      (String productUuid, Pageable pageable) throws JmXRefException {
        return createCategoryXRefRetriever(productUuid, null, pageable).retrievePage(Category.class);
    }

    @Override
    public List<Category> findAllCategoriesOfProduct
      (String productUuid, Specification<Category> productSpec) throws JmXRefException {
        return createCategoryXRefRetriever(productUuid, productSpec, null).retrieveList(Category.class);
    }

    @Override
    public Page<Category> findAllCategoriesOfProduct
      (String productUuid, Specification<Category> productSpec, Pageable pageable) throws JmXRefException {
        return createCategoryXRefRetriever(productUuid, productSpec, pageable).retrievePage(Category.class);
    }
}
