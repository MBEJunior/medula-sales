package com.medulasales.products.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.medulasales.products.entities.Product;
import com.medulasales.products.services.dto.product.ProductToCreateDto;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ProductServiceImplTest Should All Succed")
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DisplayName("CreateAllProducts Should Succed")
    void Test_CreateAllProducts_ShouldSucced() {
    }

    @Test
    @DisplayName("CreateProduct Should Succed")
    void Test_CreateProduct_ShouldSucced() {
        Exception exception = assertThrows(Exception.class, () -> {
            ProductToCreateDto productToCreateDto = ProductToCreateDto.builder().build();
            productService.createProduct(productToCreateDto);
        });
        exception.printStackTrace();
        assertTrue(exception instanceof ConstraintViolationException);
    }

    @Test
    @DisplayName("UpdateAllProducts Should Succed")
    void Test_UpdateAllProducts_ShouldSucced() {
    }

    @Test
    @DisplayName("UpdateProduct Should Succed")
    void Test_UpdateProduct_ShouldSucced() {
    }

    @Test
    @DisplayName("PatchAllProducts Should Succed")
    void Test_PatchAllProducts_ShouldSucced() {
    }

    @Test
    @DisplayName("PatchProduct Should Succed")
    void Test_PatchProduct_ShouldSucced() {
    }

    @Test
    @DisplayName("DeleteAllProducts Should Succed")
    void Test_DeleteAllProducts_ShouldSucced() {
    }

    @Test
    @DisplayName("DeleteProduct Should Succed")
    void Test_DeleteProduct_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProduct Should Succed")
    void Test_FindProduct_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProduct1 Should Succed")
    void Test_FindProduct1_ShouldSucced() {
    }

    @Test
    @DisplayName("FindAllProducts Should Succed")
    void Test_FindAllProducts_ShouldSucced() {
    }

    @Test
    @DisplayName("FindAllProducts1 Should Succed")
    void Test_FindAllProducts1_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProductsPage Should Succed")
    void Test_FindProductsPage_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProductsPage1 Should Succed")
    void Test_FindProductsPage1_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProductByName Should Succed")
    void Test_FindProductByName_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProductByName1 Should Succed")
    void Test_FindProductByName1_ShouldSucced() {
    }

    @Test
    @DisplayName("FindAllProductsByCategory Should Succed")
    void Test_FindAllProductsByCategory_ShouldSucced() {
    }

    @Test
    @DisplayName("FindAllProductsByCategory1 Should Succed")
    void Test_FindAllProductsByCategory1_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProductsPageByCategory Should Succed")
    void Test_FindProductsPageByCategory_ShouldSucced() {
    }

    @Test
    @DisplayName("FindProductsPageByCategory1 Should Succed")
    void Test_FindProductsPageByCategory1_ShouldSucced() {
    }

    @Test
    @DisplayName("UpdateProductPicture Should Succed")
    void Test_UpdateProductPicture_ShouldSucced() {
    }

    @Test
    @DisplayName("RemoveProductPicture Should Succed")
    void Test_RemoveProductPicture_ShouldSucced() {
    }
}
