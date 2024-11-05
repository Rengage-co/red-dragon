package ai.rengage.logging;

import com.alibaba.ttl.TransmittableThreadLocal;

public class RengageTraceContext {
//    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();
    private static final TransmittableThreadLocal<String> TRACE_ID = new TransmittableThreadLocal<>();

    public static void setTraceId(String traceId) {
        TRACE_ID.set(traceId);
    }

    public static String getTraceId() {
        return TRACE_ID.get();
    }

    public static void clear() {
        TRACE_ID.remove();
    }
}
