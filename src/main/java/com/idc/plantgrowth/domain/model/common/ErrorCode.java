package com.idc.plantgrowth.domain.model.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    OK("OK", "Success"),

    NOT_FOUND("NOT_FOUND", "Resource not found"),
    INVALID_STATE("INVALID_STATE", "Invalid state"),
    DUPLICATE("DUPLICATE", "Duplicate data"),
    FORBIDDEN("FORBIDDEN", "Access denied"),
    UNAUTHORIZED("UNAUTHORIZED", "Unauthorized"),
    INTERNAL_ERROR("INTERNAL_ERROR", "Internal server error"),

    VALIDATION_FAILED("VALIDATION_FAILED", "Validation failed"),
    USER_NOT_FOUND("USER_NOT_FOUND", "User not found"),
    LICENSE_EXPIRED("LICENSE_EXPIRED", "License expired"),
    ORG_NOT_ACTIVE("ORG_NOT_ACTIVE", "Organization is not active");

    private final String code;
    private final String defaultMessage;
}