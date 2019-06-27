package com.medulasales.products.entities;

import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static com.medulasales.products.entities.ParameterizedInput.Issue.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * The ProductTest class
 *
 * @author Junior Mbe
 * @version 1.0
 * @since 26/06/2019
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("ProductTest Should All Succed")
class ProductTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ValidatorFactory validatorFactory;

    private Validator validator;

    @BeforeAll
    private void init() {
        this.validator = validatorFactory.getValidator();
    }

    @DisplayName("Validate products test")
    @ParameterizedTest(name = "{index} : {6}")
    @CsvFileSource(resources = "/testdata/entities/products-validation-test-data.csv", numLinesToSkip = 1)
    public void ValidateProductsTest(@AggregateWith(ProductAggregator.class) ParameterizedInput<Product> productInput) {
        // Given : Validate product
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(productInput.getValue());

        // When : product input issue is 'succed'
        if(productInput.getIssue() == SUCCED) {
            // Then : validation should succed
            assertThat(constraintViolations.isEmpty(), is(true));
        }

        // When : product input issue is 'fail'
        else if(productInput.getIssue() == FAIL) {
            // Then : validation should faild
            assertTrue(constraintViolations.size() > 0);
        }
    }

    @DisplayName("Create products test with databases constraints")
    @ParameterizedTest(name = "{index} : {6}")
    @CsvFileSource(resources = "/testdata/entities/products-creation-test-data.csv", numLinesToSkip = 1)
    @Transactional
    public void CreateProductsTest(@AggregateWith(ProductAggregator.class) ParameterizedInput<Product> productInput) {
        // Given : products loaded from .csv

        // When : product input issue is 'succed'
        if(productInput.getIssue() == SUCCED) {
            // Then : creation should succed
            entityManager.persist(productInput.getValue());
        }

        // When : product input issue is 'exception'
        if(productInput.getIssue() == EXCEPTION) {
            // Then : creation should throws exception
            Exception exception = assertThrows(Exception.class, () -> {
                entityManager.persist(productInput.getValue());
            });
            assertEquals(exception.getClass().getSimpleName(), productInput.getExceptionClass());
        }
    }

    @Test
    @DisplayName("Create product with non unique name should faild")
    @Transactional
    public void CreateProduct_With_NonUniqueName_Should_Faild() {
        //Given : Two product with same name
        Product p1 = Product.create("Samsung galaxy s6", "Samsung galaxy s6", 5f, 120_000f, null, null);
        Product p2 = Product.create("Samsung galaxy s6", "Samsung galaxy s6", 5f, 120_000f, null, null);

        // When : Save first product
        entityManager.persist(p1);

        // Then : second product save should faild
        PersistenceException exception = assertThrows(PersistenceException.class, () -> {
            entityManager.persist(p2);
        });
    }

    @Test
    @DisplayName("Create product should generate id and uuid")
    @Transactional
    public void createProduct_Should_GenerateUuid() {
        //Given : one product to be create
        Product p = Product.create("Samsung galaxy s6", "Samsung galaxy s6", 5f, 120_000f, null, null);

        // When : Save the product
        entityManager.persist(p);

        // Then : id and uuid has been generated
        assertAll("Id and uuid",
          () -> assertNotNull(p.getId()),
          () -> assertTrue(p.getId() > 0),
          () -> assertNotNull(p.getUuid()),
          () -> assertEquals(p.getUuid().length(), 32));
    }

    @Test
    @DisplayName("Create product should set createdDate, updatedDate, creatorUuid and modifierUuid")
    @Transactional
    public void createProduct_Should_SetCreatedDate() {
        //Given : one product to be create
        Product p = Product.create("Samsung galaxy s6", "Samsung galaxy s6", 5f, 120_000f, null, null);

        // When : Save the product
        entityManager.persist(p);

        // Then : id has been generated
        assertAll("CreatedDate, updatedDate, creatorUuid and modifierUuid",
          () -> assertNotNull(p.getCreatedDate()),
          () -> assertNotNull(p.getModifiedDate()),
          () -> assertNotNull(p.getCreatorUuid()),
          () -> assertNotNull(p.getModifierUuid()));
    }

    @Test
    @DisplayName("Products without same reference but same fields values should be equals and have same hashcode" )
    @Transactional
    public void ProductsEquals_Should_Succed() {
        // Given : Create product and detach it from persistence context
        Product p1 = Product.create("Samsung galaxy s6", "Samsung galaxy s6", 5f, 120_000f, null, null);
        Product p2 = Product.create("Apple iphone 6", "Apple iphone 6", 3f, 150_000f, null, null);
        entityManager.persist(p1);
        entityManager.persist(p2);
        entityManager.detach(p1);

        // When : get product from database with same id
        Product p3 = entityManager.find(Product.class, p1.getId());

        // Then : both product will be equals and have same hashcode
        assertAll("Equals and same hashcode",
          () -> assertTrue(!entityManager.contains(p1)),
          () -> assertNotEquals(p1, p2),
          () -> assertNotEquals(p1.hashCode(), p2.hashCode()),
          () -> assertEquals(p1, p3),
          () -> assertEquals(p1.hashCode(), p3.hashCode()));
    }

    @Test
    @DisplayName("Soft delete should succed")
    @Transactional
    public void SoftDelete_Should_Succed() {
        // Given : we save product to database
        Product p1 = Product.create("Samsung galaxy s6", "Samsung galaxy s6", 5f, 120_000f, null, null);
        entityManager.persist(p1);

        // When : Perform delete and get by id
        entityManager.remove(p1);
        Query query = entityManager.createNativeQuery("SELECT product.* FROM product WHERE id = ?", Product.class);
        query.setParameter(1, p1.getId());
        Product p2 = (Product) query.getSingleResult();

        // Then : Unique keys are cuted and pasted into deletionTrace and delete field will set at true
        assertAll("Unique keys cutted and pasted into deletionTrace and delete set to true",
          () -> assertNull(p2.getName()),
          () -> assertNotNull(p2.getDeletionTrace()),
          () -> assertThat(p2.getDeletionTrace(), containsString("name:"+p1.getName())),
          () -> assertTrue(p2.isDeleted()));
    }
}
