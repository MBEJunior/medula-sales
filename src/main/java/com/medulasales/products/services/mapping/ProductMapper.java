package com.medulasales.products.services.mapping;

import com.medulasales.products.entities.Product;
import com.medulasales.products.services.dto.product.ProductFullDto;
import com.medulasales.products.services.dto.product.ProductToCreateDto;
import com.medulasales.products.services.dto.product.ProductToPatchDto;
import com.medulasales.products.services.dto.product.ProductToUpdateDto;
import com.medulasales.products.utils.uniql.PageRequest;
import com.medulasales.products.utils.uniql.Uniql;
import org.hibernate.annotations.Source;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Mapper(
  unmappedSourcePolicy = ReportingPolicy.IGNORE,
  nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
  nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
  componentModel = "spring"
)
public interface ProductMapper {

    Product Product_From_ProductToCreateDto(ProductToCreateDto createDto);
    List<Product> Products_From_ProductToCreateDtos(List<ProductToCreateDto> createDtos);
    void updateProduct_From_ProductToUpdateDto(ProductToUpdateDto updateDto, @MappingTarget Product product);

    @SuppressWarnings("Duplicates")
    default void patchProduct_From_ProductToPatchDto(ProductToPatchDto patchDto, Product product) {
        if (patchDto != null && product != null){
            if (product.getName() != null) {
                patchDto.setName(product.getName());
            }
            if (product.getDescription() != null) {
                patchDto.setDescription(product.getDescription());
            }
            if (product.getUnitPrice() != null) {
                patchDto.setUnitPrice(product.getUnitPrice());
            }
        }
    }

    default ProductToUpdateDto ProductToUpdate_From_ProductToPatchDto_And_Product(ProductToPatchDto patchDto, Product product) {
        if(patchDto == null || product == null) {
            return null;
        }
        ProductToUpdateDto updateDto = new ProductToUpdateDto();
        updateDto.setUuid(product.getUuid());
        updateDto.setName(Optional.ofNullable(patchDto.getName()).orElse(product.getName()));
        updateDto.setDescription(Optional.ofNullable(patchDto.getDescription()).orElse(product.getDescription()));
        updateDto.setUnitPrice(Optional.of(patchDto.getUnitPrice()).orElse(product.getUnitPrice()));
        return updateDto;
    }

    default ProductFullDto ProductFullDto_From_Product(Product product, Uniql filter) {
        if (product == null) {
            return null;
        } else {
            ProductFullDto productFullDto = new ProductFullDto();
            if (product.getUuid() != null && (filter == null || !filter.hasFields() || filter.hasField("uuid"))) {
                productFullDto.setUuid(product.getUuid());
            }

            if (product.getName() != null && (filter == null || !filter.hasFields() || filter.hasField("name"))) {
                productFullDto.setName(product.getName());
            }

            if (product.getDescription() != null && (filter == null || !filter.hasFields() || filter.hasField("description"))) {
                productFullDto.setDescription(product.getDescription());
            }

            if (product.getQuantiy() != null && (filter == null || !filter.hasFields() || filter.hasField("quantity"))) {
                productFullDto.setQuantiy(product.getQuantiy());
            }

            if (product.getUnitPrice() != null && (filter == null || !filter.hasFields() || filter.hasField("unitPrice"))) {
                productFullDto.setUnitPrice(product.getUnitPrice());
            }

            if (product.getPictureUuid() != null && (filter == null || !filter.hasFields() || filter.hasField("pictureUuid"))) {
                productFullDto.setPictureUuid(product.getPictureUuid());
            }

            if (product.getCreatedDate() != null && (filter == null || !filter.hasFields() || filter.hasField("createdDate"))) {
                productFullDto.setCreatedDate(product.getCreatedDate());
            }

            if (product.getModifiedDate() != null && (filter == null || !filter.hasFields() || filter.hasField("modifiedDate"))) {
                productFullDto.setModifiedDate(product.getModifiedDate());
            }

            if (product.getCreatorUuid() != null && (filter == null || !filter.hasFields() || filter.hasField("creatorUuid"))) {
                productFullDto.setCreatorUuid(product.getCreatorUuid());
            }

            if (product.getModifierUuid() != null && (filter == null || !filter.hasFields() || filter.hasField("modifierUuid"))) {
                productFullDto.setModifierUuid(product.getModifierUuid());
            }

            return productFullDto;
        }
    }

    default ProductFullDto ProductFullDto_From_Product(Product product) {
        return ProductFullDto_From_Product(product, null);
    }

    default List<ProductFullDto> ProductFullDtos_From_Products(List<Product> products, Uniql filter) {
        if (products == null) {
            return null;
        } else {
            List<ProductFullDto> list = new ArrayList(products.size());
            Iterator iterator = products.iterator();

            while(iterator.hasNext()) {
                Product product = (Product)iterator.next();
                list.add(this.ProductFullDto_From_Product(product, filter));
            }
            return list;
        }
    }

    default List<ProductFullDto> ProductFullDtos_From_Products(List<Product> products) {
        return ProductFullDtos_From_Products(products, null);
    }

    default Page<ProductFullDto> ProductFullDtos_From_Products(Page<Product> productsPage, Uniql filter) {
        if (productsPage == null) {
            return null;
        } else {
            List<ProductFullDto> list = ProductFullDtos_From_Products(productsPage.getContent(), filter);
            return new PageImpl<ProductFullDto>(list, productsPage.getPageable(), productsPage.getTotalElements());
        }
    }

    default Page<ProductFullDto> ProductFullDtos_From_Products(Page<Product> productsPage) {
        return ProductFullDtos_From_Products(productsPage, null);
    }
}
