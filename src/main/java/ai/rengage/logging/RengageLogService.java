package ai.rengage.logging;

import ch.qos.logback.classic.Level;
import net.logstash.logback.argument.StructuredArguments;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import net.logstash.logback.encoder.org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RengageLogService implements RengageLogger {
    private static final Logger logger = LoggerFactory.getLogger(RengageLogService.class);
    private static final String JAVA_VERSION = System.getProperty("java.version");
    private static final boolean IS_JAVA_9_OR_HIGHER = !JAVA_VERSION.startsWith("1.");

    private static final StackWalker STACK_WALKER = IS_JAVA_9_OR_HIGHER
            ? StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
            : null;

    @Override
    public void info(String message) {
        logWithContext(null, new HashMap<>(), Level.INFO, message, null);
    }

    @Override
    public void info(Map<String, String> arg, String message) {
        logWithContext(null, arg, Level.INFO, message, null);
    }

    @Override
    public void info(String methodName, String message) {
        logWithContext(methodName, new HashMap<>(), Level.INFO, message, null);
    }

    @Override
    public void info(String methodName, Map<String, String> arg, String message) {
        logWithContext(methodName, arg, Level.INFO, message, null);
    }

    @Override
    public void error(String message, Throwable throwable) {
        logWithContext(null, new HashMap<>(), Level.ERROR, message, throwable);
    }

    @Override
    public void error(Map<String, String> arg, String message, Throwable throwable) {
        logWithContext(null, arg, Level.ERROR, message, throwable);
    }

    @Override
    public void error(String methodName, String message, Throwable throwable) {
        logWithContext(methodName, new HashMap<>(), Level.ERROR, message, throwable);
    }

    @Override
    public void error(String methodName, Map<String, String> arg, String message, Throwable throwable) {
        logWithContext(methodName, arg, Level.ERROR, message, throwable);
    }

    @Override
    public void debug(String message) {
        logWithContext(null, new HashMap<>(), Level.DEBUG, message, null);
    }

    @Override
    public void debug(Map<String, String> arg, String message) {
        logWithContext(null, arg, Level.DEBUG, message, null);
    }

    @Override
    public void debug(String methodName, String message) {
        logWithContext(methodName, new HashMap<>(), Level.DEBUG, message, null);
    }

    @Override
    public void debug(String methodName, Map<String, String> arg, String message) {
        logWithContext(methodName, arg, Level.DEBUG, message, null);
    }

    @Override
    public void warn(String message) {
        logWithContext(null, new HashMap<>(), Level.WARN, message, null);
    }

    @Override
    public void warn(Map<String, String> arg, String message) {
        logWithContext(null, arg, Level.WARN, message, null);
    }

    @Override
    public void warn(String methodName, String message) {
        logWithContext(methodName, new HashMap<>(), Level.WARN, message, null);
    }

    @Override
    public void warn(String methodName, Map<String, String> arg, String message) {
        logWithContext(methodName, arg, Level.WARN, message, null);
    }

    private void logWithContext(String methodName, Map<String, String> arg, Level level, String message, Throwable throwable) {
        String callerClassName = getCallerClassName();
        String actualMethodName = (methodName != null) ? methodName : getCallerMethodName();
        if (Objects.isNull(arg)) {
            arg = new HashMap<>();
        }
        try (MDC.MDCCloseable ignored = MDC.putCloseable("methodName", actualMethodName)) {
            arg.put("className", callerClassName);
            arg.put("methodName", actualMethodName);
            if (StringUtils.isNotBlank(RengageTraceContext.getTraceId())) {
                arg.put("traceId", RengageTraceContext.getTraceId());
            }
            if (arg != null) {
                MDC.setContextMap(arg);
            }
            log(level, message, throwable);
        }
    }

    private String getCallerClassName() {
        if (IS_JAVA_9_OR_HIGHER) {
            return STACK_WALKER.walk(frames ->
                    frames.dropWhile(frame -> frame.getClassName().equals(RengageLogService.class.getName()))
                            .findFirst()
                            .map(StackWalker.StackFrame::getClassName)
                            .orElse(RengageLogService.class.getName())
            );
        } else {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (!element.getClassName().equals(RengageLogService.class.getName())) {
                    return element.getClassName();
                }
            }
            return RengageLogService.class.getName();
        }
    }

    private String getCallerMethodName() {
        if (IS_JAVA_9_OR_HIGHER) {
            return STACK_WALKER.walk(frames ->
                    frames.dropWhile(frame -> frame.getClassName().equals(RengageLogService.class.getName()))
                            .findFirst()
                            .map(StackWalker.StackFrame::getMethodName)
                            .orElse("unknown")
            );
        } else {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            for (StackTraceElement element : stackTrace) {
                if (!element.getClassName().equals(RengageLogService.class.getName())) {
                    return element.getMethodName();
                }
            }
            return "unknown";
        }
    }

    private void log(Level level, String message, Throwable throwable) {
        Map<String, String> logMap = new HashMap<>();
        try {
            // 收集所有MDC中的内容
            logMap.putAll(MDC.getCopyOfContextMap() != null ? MDC.getCopyOfContextMap() : new HashMap<>());

            // 如果有异常，添加异常信息
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
        } finally {
            MDC.clear();
            logMap.clear();
        }
    }
}