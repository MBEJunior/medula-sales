package com.medulasales.products.services;

import com.medulasales.products.services.dto.category.CategoryFullDto;
import com.medulasales.products.services.dto.category.CategoryToCreateDto;
import com.medulasales.products.services.dto.category.CategoryToPatchDto;
import com.medulasales.products.services.dto.category.CategoryToUpdateDto;
import com.medulasales.products.utils.uniql.Uniql;
import org.springframework.data.domain.Page;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface CategoryService {

    // Create section
    public CategoryFullDto createCategory(@Valid CategoryToCreateDto categoryToCreateDto);
    public List<CategoryFullDto> createAllCategories(List<@Valid CategoryToCreateDto> categoryToCreateDtos);

    // Update section
    public CategoryFullDto updateCategory(@Valid CategoryToUpdateDto categoryToUpdateDto);
    public List<CategoryFullDto> updateAllCategories(List<@Valid CategoryToUpdateDto> categoryToUpdateDtos);

    // Patch section
    public CategoryFullDto patchCategory(@Valid CategoryToPatchDto categoryToPatchDto);
    public List<CategoryFullDto> patchAllCategories(List<@Valid CategoryToPatchDto> categorysToPatchDtos);

    // Delete section
    public void deleteCategory(@NotNull String categoryToDeleteUuids);
    public void deleteAllCategories(@NotNull List<String> categoryToDeleteUuids);

    // Find section
    public CategoryFullDto findCategory(@NotNull String uuid);
    public CategoryFullDto findCategory(@NotNull String uuid, Uniql filters);

    public List<CategoryFullDto> findAllCategories();
    public List<CategoryFullDto> findAllCategories(Uniql filters);

    public Page<CategoryFullDto> findCategoriesPage();
    public Page<CategoryFullDto> findCategoriesPage(Uniql filters);

    // By unique setName
    public CategoryFullDto findCategoryByName(@NotNull String name);
    public CategoryFullDto findCategoryByName(@NotNull String name, Uniql filters);

    // By product
    public List<CategoryFullDto> findAllCategoriesByProduct(@NotNull String productUuid);
    public List<CategoryFullDto> findAllCategoriesByProduct(@NotNull String productUuid, Uniql filters);

    public Page<CategoryFullDto> findCategoriesPageByProduct(@NotNull String productUuid);
    public Page<CategoryFullDto> findCategoriesPageByProduct(@NotNull String productUuid, Uniql filters);

    // Embedded operations section
    public void addSubCategoryInCategory(@NotNull String categoryuuid, @NotNull String subCategoryUuid);
    public void addAllSubCategoriesInCategory(@NotNull String categoryUuid, @NotNull List<String> subCategoryUuids);

    public void removeSubCategoryFromCategory(@NotNull String categoryUuid, @NotNull String subCategoryUuid);
    public void removeAllSubCategoriesFromCategory(@NotNull String categoryUuid, @NotNull String... subCategoryUuids);

    public void addProductInCategory(@NotNull String categoryUuid, @NotNull String productUuid);
    public void addProductInAllCategories(@NotNull String productUuid, @NotNull List<String> categoruUuids);
    public void addAllProductsInCategory(@NotNull String categoryUuid, @NotNull List<String> productUuids);

    public void removeProductFromCategory(@NotNull String categoryUuid, @NotNull String productUuid);
    public void removeProductFromAllCategories(@NotNull String productUuid, @NotNull List<String> categoryUuids);
    public void removeProductFromAllHisCategories(@NotNull String productUuid);
    public void removeAllProductsFromCategory(@NotNull String categoryUuid, @NotNull List<String> productUuids);
}
