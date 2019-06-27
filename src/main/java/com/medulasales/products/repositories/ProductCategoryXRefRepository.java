package com.medulasales.products.repositories;

import com.medulasales.products.entities.ProductCategoryXRef;
import com.medulasales.products.repositories.extensions.ProductCategoryXRefRepositoryExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductCategoryXRefRepository extends
  JpaRepository<ProductCategoryXRef, Long>,
  JpaSpecificationExecutor<ProductCategoryXRef>,
  ProductCategoryXRefRepositoryExtension {

    // Find section
    Optional<ProductCategoryXRef> findByProductUuidAndCategoryUuid(String productUuid, String categoryUuid);

    // Exists section
    boolean existsByProductUuid(String productUuid);
    boolean existsByCategoryUuid(String categoryUuid);
    boolean existsByProductUuidAndCategoryUuid(String productUuid, String categoryUuid);

    // Count section
    Long countAllByProductUuid(String productUuid);
    Long countAllByCategoryUuid(String categoryUuid);

    Long countAllByCreatorUuid(String creatorUuid);
    Long countAllByModifierUuid(String modifierUuid);
}
