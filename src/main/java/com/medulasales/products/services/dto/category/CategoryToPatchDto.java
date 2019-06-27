package com.medulasales.products.services.dto.category;

import com.medulasales.products.services.dto.product.ProductFullDto;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/////////////
// Code generation annotations
/////////////
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)

public class CategoryToPatchDto {

    @NotNull(message = "Category uuid Cannot be null")
    @EqualsAndHashCode.Include
    private String uuid;

    @Min(value = 2, message = "Category name must hase more than one characters")
    @Max(value = 100, message = "Category name cannot have more than 100 characters")
    @EqualsAndHashCode.Include
    private String name;

    private String description;

    List<CategoryToPatchDto> subCategories;

    List<ProductFullDto> products;

    private CategoryToPatchDto(String uuid, String name, String description, List<CategoryToPatchDto> subCategories, List<ProductFullDto> products) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.subCategories = subCategories;
        this.products = products;
    }

    @Builder
    private static CategoryToPatchDto createCategoryFullDto(String uuid, String name, String description, List<CategoryToPatchDto> subCategories, List<ProductFullDto> products) {
        return new CategoryToPatchDto(uuid, name, description, subCategories, products);
    }
}
