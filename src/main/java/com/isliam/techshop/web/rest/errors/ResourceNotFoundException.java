package com.isliam.techshop.web.rest.errors;

public class ResourceNotFoundException extends BadRequestAlertException {
    public ResourceNotFoundException() {
        super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, "Resource not found!", "", "");
    }
}
