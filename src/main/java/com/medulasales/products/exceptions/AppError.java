package com.medulasales.products.exceptions;

import lombok.Getter;

@Getter
enum AppError {
    PRODUCT_ALREADY_EXISTS_BY_NAME(ErrorCode.ERR0001, "A product with the name '%s' already exists"),
    PRODUCT_NOT_FOUND_BY_NAME(ErrorCode.ERR0002, "Product with the name '%s' not found"),
    PRODUCT_NOT_FOUND_BY_UUID(ErrorCode.ERR0003, "Product with uuid '%s' not found"),
    CATEGORY_NOT_FOUND_BY_NAME(ErrorCode.ERR0004, "Category with the name '%s' not found"),
    CATEGORY_NOT_FOUND_BY_UUID(ErrorCode.ERR0005, "Category with uuid '%s' not found"),
    MEDIA_IMAGE_INVALID(ErrorCode.ERR0006, "Le media '%s' n'est pas une image valide"),
    RESULT_COUNT_TOO_LONG(ErrorCode.ERR0007, "La liste des résultats est trop longue. vous ne pouvez pas charger plus de %d éléments à la fois");

    private ErrorCode code;
    private String messageTemplate;

    AppError(ErrorCode code, String messageTemplate) {
        this.code = code;
        this.messageTemplate = messageTemplate;
    }
}
