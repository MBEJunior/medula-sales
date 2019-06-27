package com.medulasales.products.services.dto.category;

import com.medulasales.products.services.dto.product.ProductFullDto;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/////////////
// Code generation annotations
/////////////
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)

public class CategoryFullDto {

    @NotNull(message = "Category uuid Cannot be null")
    @EqualsAndHashCode.Include
    private String uuid;

    @NotNull(message = "Category name cannot be null")
    @Min(value = 2, message = "Category name must hase more than one characters")
    @Max(value = 100, message = "Category name cannot have more than 100 characters")
    @EqualsAndHashCode.Include
    private String name;

    private String description;

    List<CategoryFullDto> subCategories;

    List<ProductFullDto> products;

    @Setter(AccessLevel.NONE)
    private Date createdDate;

    @Setter(AccessLevel.NONE)
    private Date modifiedDate;

    @Setter(AccessLevel.NONE)
    private String creatorId;

    @Setter(AccessLevel.NONE)
    private String modifierId;

    private CategoryFullDto(String uuid, String name, String description, List<CategoryFullDto> subCategories, List<ProductFullDto> products) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.subCategories = subCategories;
        this.products = products;
    }

    @Builder
    private static CategoryFullDto createCategoryFullDto(String uuid, String name, String description, List<CategoryFullDto> subCategories, List<ProductFullDto> products) {
        return new CategoryFullDto(uuid, name, description, subCategories, products);
    }
}
