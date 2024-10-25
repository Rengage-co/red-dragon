package ai.rengage.logging;

import ch.qos.logback.classic.Level;
import net.logstash.logback.argument.StructuredArguments;
import net.logstash.logback.encoder.org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

public class RengageLogService {
    private static final Logger logger = LoggerFactory.getLogger(RengageLogService.class);
    private final String className;

    public RengageLogService(String className) {
        this.className = className;
    }

    public void info(String methodName, Map<String, String> arg, String message) {
        logWithContext(methodName, arg, Level.INFO, message, null);
    }

    public void error(String methodName, Map<String, String> arg, String message, Throwable throwable) {
        logWithContext(methodName, arg, Level.ERROR, message, throwable);
    }

    public void debug(String methodName, Map<String, String> arg, String message) {
        logWithContext(methodName, arg, Level.DEBUG, message, null);
    }

    public void warn(String methodName, Map<String, String> arg, String message) {
        logWithContext(methodName, arg, Level.WARN, message, null);
    }

    private void logWithContext(String methodName, Map<String, String> arg, Level level, String message, Throwable throwable) {
        try (MDC.MDCCloseable ignored = MDC.putCloseable("methodName", methodName)) {
            if (arg != null) {
                MDC.setContextMap(arg);
            }
            log(level, message, throwable);
        } finally {
            MDC.clear();
        }
    }

    private void log(Level level, String message, Throwable throwable) {
        try {
            Map<String, String> logMap = new HashMap<>();
            logMap.put("className", className);
            if (throwable != null) {
                logMap.put("exception", ExceptionUtils.getStackTrace(throwable));
            }
            switch (level.levelStr.toLowerCase()) {
                case "warn":
                    logger.warn(message, StructuredArguments.entries(logMap));
                    break;
                case "error":
                    logger.error(message, StructuredArguments.entries(logMap));
                    break;
                case "debug":
                    logger.debug(message, StructuredArguments.entries(logMap));
                    break;
                default:
                    logger.info(message, StructuredArguments.entries(logMap));
            }
        } catch (Exception e) {
            logger.error("Error while logging: " + e.getMessage(), e);
        }
    }
}
