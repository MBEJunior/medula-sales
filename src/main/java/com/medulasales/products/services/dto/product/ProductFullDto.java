package com.medulasales.products.services.dto.product;

import com.medulasales.products.services.dto.category.CategoryFullDto;
import lombok.*;

import javax.validation.constraints.DecimalMin;
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

public class ProductFullDto {

    @NotNull(message = "Product uuid Cannot be null")
    @EqualsAndHashCode.Include
    private String uuid;

    @NotNull(message = "Product name cannot be null")
    @Min(value = 2, message = "Product name must hase more than one characters")
    @Max(value = 100, message = "Product name cannot have more than 100 characters")
    @EqualsAndHashCode.Include
    private String name;

    private String description;

    @NotNull(message = "Product quantity cannot be null")
    @DecimalMin(value = "0.00", message = "Product quantity cannot be less than 0")
    private Float quantiy;

    @NotNull(message = "Product unit price cannot be null")
    @DecimalMin(value = "0.00", message = "Product unit price have to be greater or equals to 0.00")
    private Float unitPrice;

    private String pictureUuid;

    List<CategoryFullDto> categories;

    private Date createdDate;

    private Date modifiedDate;

    private String creatorUuid;

    private String modifierUuid;

    private ProductFullDto(@NotNull(message = "Product uuid Cannot be null") String uuid, @NotNull(message = "Product setName cannot be null") @Min(value = 2, message = "Product setName must hase more than one characters") @Max(value = 100, message = "Product setName cannot have more than 100 characters") String name, String description, @NotNull(message = "Product quantity cannot be null") @DecimalMin(value = "0.00", message = "Product quantity cannot be less than 0") Float quantiy, @NotNull(message = "Product unit price cannot be null") @DecimalMin(value = "0.00", message = "Product unit price have to be greater or equals to 0.00") Float unitPrice, String pictureUuid, List<CategoryFullDto> categories, Date createdDate, Date modifiedDate, String creatorUuid, String modifierUuid) {
        this.uuid = uuid;
        this.name = name;
        this.description = description;
        this.quantiy = quantiy;
        this.unitPrice = unitPrice;
        this.pictureUuid = pictureUuid;
        this.categories = categories;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.creatorUuid = creatorUuid;
        this.modifierUuid = modifierUuid;
    }

    @Builder
    public static ProductFullDto create(@NotNull(message = "Product uuid Cannot be null") String uuid, @NotNull(message = "Product setName cannot be null") @Min(value = 2, message = "Product setName must hase more than one characters") @Max(value = 100, message = "Product setName cannot have more than 100 characters") String name, String description, @NotNull(message = "Product quantity cannot be null") @DecimalMin(value = "0.00", message = "Product quantity cannot be less than 0") Float quantiy, @NotNull(message = "Product unit price cannot be null") @DecimalMin(value = "0.00", message = "Product unit price have to be greater or equals to 0.00") Float unitPrice, String pictureUuid, List<CategoryFullDto> categories, Date createdDate, Date modifiedDate, String creatorUuid, String modifierUuid) {
        return new ProductFullDto(uuid, name, description, quantiy, unitPrice, pictureUuid, categories, createdDate, modifiedDate, creatorUuid, modifierUuid);
    }
}
