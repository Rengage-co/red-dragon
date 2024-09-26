package ai.rengage.logging;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);
    private final String contextName;

    public LogService() {
        // 获取 LoggerContext 并读取 contextName
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        this.contextName = context.getName();
    }

    public void info(String message) {
        logger.info("{} - {} - {}", contextName, getCallerInfo(), message);
    }

    public void debug(String message) {
        logger.debug("{} - {} - {}", contextName, getCallerInfo(), message);
    }

    public void error(String message) {
        logger.error("{} - {} - {}", contextName, getCallerInfo(), message);
    }

    private String getCallerInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[3]; // 根据堆栈深度调整
        return caller.getClassName() + "." + caller.getMethodName() + "()";
    }
}

