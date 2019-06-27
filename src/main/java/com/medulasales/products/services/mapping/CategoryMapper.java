package com.medulasales.products.services.mapping;

import com.medulasales.products.entities.Category;
import com.medulasales.products.services.dto.category.CategoryFullDto;
import com.medulasales.products.services.dto.category.CategoryToCreateDto;
import com.medulasales.products.services.dto.category.CategoryToPatchDto;
import com.medulasales.products.services.dto.category.CategoryToUpdateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(
  unmappedSourcePolicy = ReportingPolicy.IGNORE,
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
  componentModel = "spring"
)
public interface CategoryMapper {

    Category Category_From_CategoryToCreateDto(CategoryToCreateDto createDto);
    List<Category> Categories_From_CategoryToCreateDtos(List<CategoryToCreateDto> createDtos);
    void updateCategory_From_CategoryToUpdateDto(CategoryToUpdateDto updateDto, @MappingTarget Category productcategory);

    @SuppressWarnings("Duplicates")
    default void patchCategory_From_CategoryToPatchDto(CategoryToPatchDto patchDto, @MappingTarget Category category) {
        if (patchDto != null && category != null){
            if (category.getName() != null) {
                patchDto.setName(category.getName());
            }
            if (category.getDescription() != null) {
                patchDto.setDescription(category.getDescription());
            }
        }
    }
    CategoryFullDto CategoryFullDto_From_Category(Category product);
    List<CategoryFullDto> CategoryFullDtos_From_Categories(List<Category> categories);
}
