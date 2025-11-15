package com.idc.plantgrowth.interfaces.exception;

import com.idc.plantgrowth.domain.model.common.ErrorCode;
import com.idc.plantgrowth.interfaces.common.ApiResponse;
import com.idc.plantgrowth.interfaces.common.ApiResponseFactory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Không xử lý lỗi Security
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleAuthError() {
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleForbidden() {
    }

    /**
     * KHÔNG xử lý static resource → giữ nguyên để Spring trả về trang lỗi HTML
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public void ignoreStatic404() {
        // do nothing → để BasicErrorController xử lý
    }

    /**
     * Validation error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleValidation(MethodArgumentNotValidException ex) {

        StringBuilder msg = new StringBuilder();
        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            msg.append(f.getField())
                    .append(": ")
                    .append(f.getDefaultMessage())
                    .append("; ");
        }

        return ApiResponseFactory.error(ErrorCode.VALIDATION_FAILED, msg.toString());
    }

    /**
     * Những exception còn lại → chỉ return JSON khi request là API
     */
    @ExceptionHandler(Exception.class)
    public Object handleGeneric(Exception ex, HttpServletRequest req) {

        String accept = req.getHeader("Accept");
        String uri = req.getRequestURI();

        // Nếu là request HTML hoặc static resource → trả về error mặc định (HTML)
        boolean isHtmlRequest =
                (accept != null && accept.contains("text/html"))
                        || uri.startsWith("/swagger-ui")
                        || uri.startsWith("/webjars")
                        || uri.startsWith("/v3/api-docs");

        if (isHtmlRequest) {
            // Không dùng RuntimeException(ex) → trả về lại exception gốc để Spring xử lý
            throw new RuntimeException(ex);
        }

        // API request → return JSON
        log.error("Unhandled exception at {}: {}", uri, ex.getMessage(), ex);

        return ApiResponseFactory.error(ErrorCode.INTERNAL_ERROR, ex.getMessage());
    }
}
