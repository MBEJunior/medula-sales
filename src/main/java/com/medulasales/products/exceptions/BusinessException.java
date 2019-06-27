package com.medulasales.products.exceptions;

public class BusinessException extends  AppException {
    private BusinessException(AppError errorCode, Object... args) {
        super(errorCode, args);
    }

    public static BusinessException PRODUCT_NOT_FOUND_BY_UUID(String uuid) {
        return new BusinessException(AppError.PRODUCT_NOT_FOUND_BY_UUID, uuid);
    }

    public static BusinessException PRODUCT_NOT_FOUND_BY_NAME(String name) {
        return new BusinessException(AppError.PRODUCT_NOT_FOUND_BY_NAME, name);
    }

    public static BusinessException CATEGORY_NOT_FOUND_BY_UUID(String uuid) {
        return new BusinessException(AppError.CATEGORY_NOT_FOUND_BY_UUID, uuid);
    }

    public static BusinessException CATEGORY_NOT_FOUND_BY_NAME(String name) {
        return new BusinessException(AppError.CATEGORY_NOT_FOUND_BY_NAME, name);
    }

    public static BusinessException PRODUCT_NAME_ALREADY_EXIST(String productName) {
        return new BusinessException(AppError.PRODUCT_ALREADY_EXISTS_BY_NAME, productName);
    }

    public static BusinessException MEDIA_IMAGE_INVALID(String mediaName) {
        return new BusinessException(AppError.MEDIA_IMAGE_INVALID, mediaName);
    }

    public static BusinessException RESULT_COUNT_TOO_LONG(int maxCount) {
        return new BusinessException(AppError.RESULT_COUNT_TOO_LONG, maxCount);
    }
}
