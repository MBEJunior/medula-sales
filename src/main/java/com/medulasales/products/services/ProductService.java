package com.medulasales.products.services;

import com.medulasales.products.exceptions.BusinessException;
import com.medulasales.products.services.dto.product.*;
import com.medulasales.products.utils.uniql.Uniql;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface ProductService {

    // Create section
    public ProductFullDto createProduct(@Valid ProductToCreateDto productToCreateDto) throws BusinessException;
    public List<ProductFullDto> createAllProducts(List<@Valid ProductToCreateDto> productToCreateDtos) throws BusinessException;

    // Update section
    public ProductFullDto updateProduct(@Valid ProductToUpdateDto productToUpdateDto) throws BusinessException;
    public List<ProductFullDto> updateAllProducts(List<@Valid ProductToUpdateDto> productToUpdateDtos) throws BusinessException;

    // Patch section
    public ProductFullDto patchProduct(@Valid ProductToPatchDto productToPatchDto) throws BusinessException;
    public List<ProductFullDto> patchAllProducts(List<@Valid ProductToPatchDto> productsToPatchDtos) throws BusinessException;

    // Delete section
    public void deleteProduct(String productToDeleteUuids) throws BusinessException;
    public void deleteAllProducts(@NotNull List<String> productToDeleteUuids) throws BusinessException;

    // Find section
    public ProductFullDto findProduct(@NotNull String uuid, Uniql filter) throws BusinessException;
    public ProductFullDto findProduct(@NotNull String uuid) throws BusinessException;

    // By unique name
    public ProductFullDto findProductByName(@NotNull String name, Uniql filter) throws BusinessException;
    public ProductFullDto findProductByName(@NotNull String name) throws BusinessException;

    public List<ProductFullDto> findAllProducts(Uniql filter) throws BusinessException;
    public List<ProductFullDto> findAllProducts() throws BusinessException;
    public Page<ProductFullDto> findProductsPage(Uniql filter) throws BusinessException;

    // By category
    public List<ProductFullDto> findAllProductsByCategory(@NotNull String categoryUuid, Uniql filter) throws BusinessException;
    public List<ProductFullDto> findAllProductsByCategory(@NotNull String categoryUuid) throws BusinessException;
    public Page<ProductFullDto> findProductsPageByCategory(@NotNull String categoryUuid, Uniql filter) throws BusinessException;

    // Embedded operations section
    public ProductFullDto setProductPicture(@NotNull String productUuid, MultipartFile pictureFile) throws BusinessException;
    public ProductFullDto removeProductPicture(@NotNull String productUuid) throws BusinessException;
}
