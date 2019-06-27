package com.medulasales.products.repositories;

import com.medulasales.products.entities.Category;
import com.medulasales.products.repositories.extensions.CategoryRepositoryExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category>, CategoryRepositoryExtension {

    // Find section
    Optional<Category> findByUuid(String uuid);
    Optional<Category> findByName(String name);

    // Exists section
    boolean existsByUuid(String uuid);
    boolean existsByName(String name);

    // Count section
    Long countAllByCreatorUuid(String creatorUuid);
    Long countAllByModifierUuid(String modifierUuid);
    Long countAllByParentCategoryId(Long parentCategoryId);
}
