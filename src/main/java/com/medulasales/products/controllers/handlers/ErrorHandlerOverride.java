package com.medulasales.products.controllers.handlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandlerOverride extends ResponseEntityExceptionHandler {
}
