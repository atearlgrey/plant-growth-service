package com.idc.plantgrowth.interfaces.common;

import com.idc.plantgrowth.domain.model.common.ErrorCode;
import com.idc.plantgrowth.infrastructure.logging.TraceIdHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Instant;
import java.util.List;

public class ApiResponseFactory {

    public static <T> ApiPagedResponse<T> paged(
            List<T> items,
            int page,
            int size,
            long totalItems
    ) {
        int totalPages = (int) Math.ceil((double) totalItems / size);

        return ApiPagedResponse.<T>builder()
                .success(true)
                .code(ErrorCode.OK.getCode())
                .message(ErrorCode.OK.getDefaultMessage())
                .data(ApiPagedResponse.PagedData.<T>builder()
                        .items(items)
                        .page(page)
                        .size(size)
                        .totalItems(totalItems)
                        .totalPages(totalPages)
                        .hasNext(page < totalPages - 1)
                        .hasPrevious(page > 0)
                        .build()
                )
                .metadata(buildMetadata())
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ErrorCode.OK.getCode())
                .message(ErrorCode.OK.getDefaultMessage())
                .data(data)
                .metadata(buildMetadata())
                .build();
    }

    public static <T> ApiResponse<T> error(ErrorCode code, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(code.getCode())
                .message(message != null ? message : code.getDefaultMessage())
                .metadata(buildMetadata())
                .build();
    }

    private static ApiResponse.Metadata buildMetadata() {

        // Lấy path request
        String path = null;
        var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null && attrs.getRequest() != null) {
            path = attrs.getRequest().getRequestURI();
        }

        return ApiResponse.Metadata.builder()
                .timestamp(Instant.now().toString())
                .traceId(TraceIdHolder.get())
                .path(path)
                .build();
    }
}
