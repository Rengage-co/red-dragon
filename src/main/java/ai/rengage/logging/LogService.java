package ai.rengage.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.LoggerContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);
    private String propertyValue="logger-service";
//    private final String contextName;

    public LogService() {
        // 获取 LoggerContext 并读取 contextName
//        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
//        this.contextName = context.getName();
        loadProperties();
    }

    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
                propertyValue = properties.getProperty("spring.application.name", "rengage-logger");
            }
        } catch (IOException e) {
            logger.error("Error loading properties", e);
        }
    }

    public void info(String message) {
        logger.info("{} - {} - {}", propertyValue, getCallerInfo(),message);
    }

    private String getCallerInfo() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stackTrace[3]; // 根据堆栈深度调整
        return caller.getClassName() + "." + caller.getMethodName() + "()";
    }

    // 其他日志方法...
}


