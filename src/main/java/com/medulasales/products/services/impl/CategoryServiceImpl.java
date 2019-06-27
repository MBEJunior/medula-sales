package com.medulasales.products.services.impl;

import com.medulasales.products.exceptions.BusinessException;
import com.medulasales.products.services.CategoryService;
import com.medulasales.products.services.dto.category.CategoryFullDto;
import com.medulasales.products.services.dto.category.CategoryToCreateDto;
import com.medulasales.products.services.dto.category.CategoryToPatchDto;
import com.medulasales.products.services.dto.category.CategoryToUpdateDto;
import com.medulasales.products.utils.uniql.Uniql;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Transactional
@Validated
public class CategoryServiceImpl implements CategoryService {
    @Override
    public CategoryFullDto createCategory(@Valid CategoryToCreateDto categoryToCreateDto) {
        return null;
    }

    @Override
    public List<CategoryFullDto> createAllCategories(List<@Valid CategoryToCreateDto> categoryToCreateDtos) {
        return null;
    }

    @Override
    public CategoryFullDto updateCategory(@Valid CategoryToUpdateDto categoryToUpdateDto) {
        return null;
    }

    @Override
    public List<CategoryFullDto> updateAllCategories(List<@Valid CategoryToUpdateDto> categoryToUpdateDtos) {
        return null;
    }

    @Override
    public CategoryFullDto patchCategory(@Valid CategoryToPatchDto categoryToPatchDto) {
        return null;
    }

    @Override
    public List<CategoryFullDto> patchAllCategories(List<@Valid CategoryToPatchDto> categorysToPatchDtos) {
        return null;
    }

    @Override
    public void deleteCategory(@NotNull String categoryToDeleteUuids) {

    }

    @Override
    public void deleteAllCategories(@NotNull List<String> categoryToDeleteUuids) {

    }

    @Override
    public CategoryFullDto findCategory(@NotNull String uuid) {
        return null;
    }

    @Override
    public CategoryFullDto findCategory(@NotNull String uuid, Uniql filters) {
        return null;
    }

    @Override
    public List<CategoryFullDto> findAllCategories() {
        return null;
    }

    @Override
    public List<CategoryFullDto> findAllCategories(Uniql filters) {
        return null;
    }

    @Override
    public Page<CategoryFullDto> findCategoriesPage() {
        return null;
    }

    @Override
    public Page<CategoryFullDto> findCategoriesPage(Uniql filters) {
        return null;
    }

    @Override
    public CategoryFullDto findCategoryByName(@NotNull String name) {
        return null;
    }

    @Override
    public CategoryFullDto findCategoryByName(@NotNull String name, Uniql filters) {
        return null;
    }

    @Override
    public List<CategoryFullDto> findAllCategoriesByProduct(@NotNull String productUuid) {
        return null;
    }

    @Override
    public List<CategoryFullDto> findAllCategoriesByProduct(@NotNull String productUuid, Uniql filters) {
        return null;
    }

    @Override
    public Page<CategoryFullDto> findCategoriesPageByProduct(@NotNull String productUuid) {
        return null;
    }

    @Override
    public Page<CategoryFullDto> findCategoriesPageByProduct(@NotNull String productUuid, Uniql filters) {
        return null;
    }

    @Override
    public void addSubCategoryInCategory(@NotNull String categoryuuid, @NotNull String subCategoryUuid) {

    }

    @Override
    public void addAllSubCategoriesInCategory(@NotNull String categoryUuid, @NotNull List<String> subCategoryUuids) {

    }

    @Override
    public void removeSubCategoryFromCategory(@NotNull String categoryUuid, @NotNull String subCategoryUuid) {

    }

    @Override
    public void removeAllSubCategoriesFromCategory(@NotNull String categoryUuid, @NotNull String... subCategoryUuids) {

    }

    @Override
    public void addProductInCategory(@NotNull String categoryUuid, @NotNull String productUuid) {

    }

    @Override
    public void addProductInAllCategories(@NotNull String productUuid, @NotNull List<String> categoruUuids) {

    }

    @Override
    public void addAllProductsInCategory(@NotNull String categoryUuid, @NotNull List<String> productUuids) {

    }

    @Override
    public void removeProductFromCategory(@NotNull String categoryUuid, @NotNull String productUuid) {

    }

    @Override
    public void removeProductFromAllCategories(@NotNull String productUuid, @NotNull List<String> categoryUuids) {

    }

    @Override
    public void removeProductFromAllHisCategories(@NotNull String productUuid) {

    }

    @Override
    public void removeAllProductsFromCategory(@NotNull String categoryUuid, @NotNull List<String> productUuids) {

    }
}
