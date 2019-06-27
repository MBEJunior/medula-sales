package com.medulasales.products.repositories.extensions;

import com.medulasales.products.entities.Category;
import com.medulasales.products.entities.Product;
import com.medulasales.products.entities.ProductCategoryXRef;
import com.medulasales.products.utils.jmgenericxrefqueryretriever.JmXRefException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductCategoryXRefRepositoryExtension {

    // Add section
    ProductCategoryXRef addProductIntoCategory(String productUuid, String categoryUuid) throws JmXRefException;

    // Remove section
    void removeProductFromCategory(String productUuid, String categoryUuid) throws JmXRefException;

    // Find section
    List<Product> findAllProductsIntoCategory(String categoryUuid) throws JmXRefException;
    Page<Product> findAllProductsIntoCategory(String categoryUuid, Pageable pageable) throws JmXRefException;
    List<Product> findAllProductsIntoCategory(String categoryUuid, Specification<Product> productSpec) throws JmXRefException;
    Page<Product> findAllProductsIntoCategory(String categoryUuid, Specification<Product> productSpec, Pageable pageable) throws JmXRefException;

    List<Category> findAllCategoriesOfProduct(String productUuid) throws JmXRefException;
    Page<Category> findAllCategoriesOfProduct(String productUuid, Pageable pageable) throws JmXRefException;
    List<Category> findAllCategoriesOfProduct(String productUuid, Specification<Category> productSpec) throws JmXRefException;
    Page<Category> findAllCategoriesOfProduct(String productUuid, Specification<Category> productSpec, Pageable pageable) throws JmXRefException;
}
