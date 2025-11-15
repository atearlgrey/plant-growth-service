package com.idc.plantgrowth.interfaces.common;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiPagedResponse<T> {

    private boolean success;
    private String code;
    private String message;

    private PagedData<T> data;
    private ApiResponse.Metadata metadata;

    @Data
    @Builder
    public static class PagedData<T> {
        private List<T> items;
        private int page;
        private int size;
        private long totalItems;
        private int totalPages;
        private boolean hasNext;
        private boolean hasPrevious;
    }
}
