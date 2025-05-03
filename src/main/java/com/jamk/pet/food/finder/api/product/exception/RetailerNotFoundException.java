package com.jamk.pet.food.finder.api.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RetailerNotFoundException extends RuntimeException {
    public RetailerNotFoundException(String message) {
        super(message);
    }
}
