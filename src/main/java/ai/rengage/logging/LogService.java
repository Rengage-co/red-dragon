package ai.rengage.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    public void info(String message) {
        logger.info(getCallerInfo() + " - " + message);
    }

    public void debug(String message) {
        logger.debug(getCallerInfo() + " - " + message);
    }

    public void error(String message) {
        logger.error(getCallerInfo() + " - " + message);
    }

    private String getCallerInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // stackTrace[2] 是调用 info/debug/error 方法的调用者
        StackTraceElement caller = stackTrace[2];
        return caller.getClassName() + "." + caller.getMethodName() + "()";
    }
}
