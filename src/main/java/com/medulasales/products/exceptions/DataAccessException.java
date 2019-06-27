package com.medulasales.products.exceptions;

class DataAccessException extends AppException {
    private DataAccessException(AppError errorCode, Object... args) {
        super(errorCode, args);
    }
}
