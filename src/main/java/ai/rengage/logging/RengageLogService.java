package ai.rengage.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

public class RengageLogService {
    private static final Logger logger = LoggerFactory.getLogger(RengageLogService.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String className;

    public RengageLogService(String className) {
        this.className = className;
    }

    public void info(String methodName,Map<String, String> arg,String message) {
        MDC.setContextMap(arg);
        MDC.put("methodName",methodName);
        log("info",message,null);
    }

    public void error(String message,Throwable throwable) {
        log("error",message,throwable);
    }

    public void debug(String message) {
        log("debug",message,null);
    }
    public void warn(String message) {
        log("warn",message,null);
    }

    private void log(String level, String message, Throwable throwable) {
        try {
//            StackWalker walker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
//            Optional<StackWalker.StackFrame> callerFrame = walker.walk(frames -> frames.skip(1).findFirst());
//            String methodName = callerFrame.map(frame -> frame.getMethodName()).orElse("Unknown");

            Map<String, String> logMap = new HashMap<>();
            logMap.put("className", className);
            if (throwable != null) {
                logMap.put("exception", throwable.toString());
//                logMap.put("stackTrace", getStackTraceAsString(throwable));
            }
//            String jsonLog = objectMapper.writeValueAsString(logMap);
            switch (level.toLowerCase()) {
                case "info":
//                    logger.info("{}-{}() {}",className,methodName,jsonLog);
                    logger.info(message,StructuredArguments.entries(logMap));
                    break;
                case "warn":
                    logger.warn(message,StructuredArguments.entries(logMap));
                    break;
                case "error":
                    logger.error(message,StructuredArguments.entries(logMap));
                    break;
                case "debug":
                    logger.debug(message,StructuredArguments.entries(logMap));
                    break;
                default:
                    logger.info(message,StructuredArguments.entries(logMap));
            }
        } catch (Exception e) {
            logger.error("Error while logging: " + e.getMessage());
        }finally {
            MDC.clear();
        }
    }
}


