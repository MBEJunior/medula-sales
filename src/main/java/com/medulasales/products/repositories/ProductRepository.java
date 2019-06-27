package com.medulasales.products.repositories;

import com.medulasales.products.entities.Product;
import com.medulasales.products.repositories.extensions.ProductRepositoryExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>, ProductRepositoryExtension {

    // Find section
    Optional<Product> findByUuid(String uuid);
    Optional<Product> findByName(String name);

    // Exists section
    boolean existsByUuid(String uuid);
    boolean existsByName(String name);

    // Count section
    Long countAllByCreatorUuid(String creatorUuid);
    Long countAllByModifierUuid(String modifierUuid);
}
