package ai.rengage.logging;

import net.logstash.logback.argument.StructuredArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

public class RengageLogService {
    private static final Logger logger = LoggerFactory.getLogger(RengageLogService.class);private final String className;

    public RengageLogService(String className) {
        this.className = className;
    }

    public void info(String methodName,Map<String, String> arg,String message) {
        MDC.setContextMap(arg);
        MDC.put("methodName",methodName);
        log("info",message,null);
    }

    public void error(String methodName,Map<String, String> arg,String message,Throwable throwable) {
        MDC.setContextMap(arg);
        MDC.put("methodName",methodName);
        log("error",message,throwable);
    }

    public void debug(String methodName,Map<String, String> arg,String message) {
        MDC.setContextMap(arg);
        MDC.put("methodName",methodName);
        log("debug",message,null);
    }
    public void warn(String methodName,Map<String, String> arg,String message) {
        MDC.setContextMap(arg);
        MDC.put("methodName",methodName);
        log("warn",message,null);
    }

    private void log(String level, String message, Throwable throwable) {
        try {
            Map<String, String> logMap = new HashMap<>();
            logMap.put("className", className);
            if (throwable != null) {
                logMap.put("exception", throwable.toString());
//                logMap.put("stackTrace", getStackTraceAsString(throwable));
            }
            switch (level.toLowerCase()) {
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


