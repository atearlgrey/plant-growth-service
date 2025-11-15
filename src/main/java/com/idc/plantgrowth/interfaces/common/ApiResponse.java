package com.idc.plantgrowth.interfaces.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    boolean success;
    String code;
    String message;
    T data;
    Metadata metadata;

    @Value
    @Builder
    public static class Metadata {
        String timestamp;
        String traceId;
        String path;
    }
}
