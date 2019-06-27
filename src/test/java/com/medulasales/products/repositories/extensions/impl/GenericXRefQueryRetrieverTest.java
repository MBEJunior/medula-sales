package com.medulasales.products.repositories.extensions.impl;

import com.medulasales.products.entities.*;
import com.medulasales.products.repositories.CategoryRepository;
import com.medulasales.products.repositories.ProductCategoryXRefRepository;
import com.medulasales.products.repositories.ProductRepository;
import com.medulasales.products.utils.jmgenericxrefqueryretriever.JmXRefRetriever;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Generic Cross Reference Retriever Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenericXRefQueryRetrieverTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductCategoryXRefRepository productCategoryXrefRepository;

    @Test
    @Order(0)
    void populateDatabase() {
        Category category1 = Category.builder()
          .name("Téléphones")
          .description("Téléphones")
          .build();
        categoryRepository.save(category1);

        Product product1 = Product.builder()
          .name("Samsung Galaxy S4")
          .description("Samsung Galaxy S4")
          .quantiy(4f)
          .unitPrice(60_000f)
          .build();
        productRepository.save(product1);

        assertNotNull(product1.getId());

        Product product2 = Product.builder()
          .name("Huawei p10")
          .description("Huawei p10")
          .quantiy(4f)
          .unitPrice(250_000f)
          .build();
        productRepository.save(product2);

        ProductCategoryXRef pXc1 = ProductCategoryXRef.builder()
          .category(category1)
          .product(product1)
          .build();
        productCategoryXrefRepository.save(pXc1);

        ProductCategoryXRef pXc2 = ProductCategoryXRef.builder()
          .category(category1)
          .product(product2)
          .build();
        productCategoryXrefRepository.save(pXc2);
    }

    @Test
    @DisplayName("Count With Valids Arguments Should Succed")
    void Count_WithValidsArguments_ShouldSucced() {
        assertDoesNotThrow(()->{

            Long productCountByCategory = new JmXRefRetriever<Product, ProductCategoryXRef>(entityManager)
              .from(Product.class)
              .through(ProductCategoryXRef.class)
              .collectBy(Product_.PRODUCT_CATEGORY_XREFS)
              .countBy(Product_.ID)
              .count();

            assertEquals(productCountByCategory, (Long)2L);
        });
    }

    @Test
    @DisplayName("Get Single Value With Valids Arguments Should Succed")
    void GetSingleValue_WithValidsArguments_ShouldSucced() {
        assertDoesNotThrow(()->{

            Product product = new JmXRefRetriever<Product, ProductCategoryXRef>(entityManager)
              .from(Product.class)
              .through(ProductCategoryXRef.class)
              .collectBy(Product_.PRODUCT_CATEGORY_XREFS)
              .sortBy(Sort.by(Sort.Direction.DESC, Product_.ID))
              .countBy(Product_.ID)
              .retrieveOne(Product.class);

            assertNotNull(product);
        });
    }

    @Test
    @DisplayName("Retrieve List With Valids Arguments Should Succed")
    void RetrieveList_WithValidsArguments_ShouldSucced() {

    }

    @DisplayName("Retrieve PageRequest With Valids Arguments Should Succed")
    void RetrievePage_WithValidsArguments_ShouldSucced() {

    }
}
