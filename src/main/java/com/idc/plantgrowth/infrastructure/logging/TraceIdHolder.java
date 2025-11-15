package com.idc.plantgrowth.infrastructure.logging;

public class TraceIdHolder {

    private static final ThreadLocal<String> holder = new ThreadLocal<>();

    public static void set(String traceId) {
        holder.set(traceId);
    }

    public static String get() {
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
