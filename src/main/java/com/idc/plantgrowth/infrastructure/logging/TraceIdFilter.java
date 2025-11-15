package com.idc.plantgrowth.infrastructure.logging;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE) // chạy sớm nhất
public class TraceIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            HttpServletRequest req = (HttpServletRequest) request;

            // Lấy trace-id từ header nếu có
            String traceId = req.getHeader("X-Trace-Id");
            if (traceId == null || traceId.isBlank()) {
                traceId = UUID.randomUUID().toString();
            }

            TraceIdHolder.set(traceId);

            chain.doFilter(request, response);

        } finally {
            TraceIdHolder.clear(); // tránh leak ThreadLocal
        }
    }
}