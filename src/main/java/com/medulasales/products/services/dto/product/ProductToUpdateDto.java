package com.medulasales.products.services.dto.product;

import com.medulasales.products.services.dto.category.CategoryFullDto;
import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
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

public class ProductToUpdateDto {

    @NotNull(message = "Product uuid Cannot be null")
    @EqualsAndHashCode.Include
    private String uuid;

    @EqualsAndHashCode.Include
    @NotNull(message = "Product name cannot be null")
    @Min(value = 2, message = "Product name must hase more than one characters")
    @Max(value = 100, message = "Product name cannot have more than 100 characters")
    private String name;

    private String description;

    @NotNull(message = "Product unit price cannot be null")
    @DecimalMin(value = "0.00", message = "Product unit price have to be greater or equals to 0.00")
    private Float unitPrice;

    private ProductToUpdateDto(String uuid, String name, String description, Float unitPrice) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    @Builder
    public static ProductToUpdateDto create(String uuid, String name, String description, Float unitPrice) {
        return new ProductToUpdateDto(uuid, name, description, unitPrice);
    }
}
