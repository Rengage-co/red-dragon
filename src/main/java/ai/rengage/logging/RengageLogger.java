package ai.rengage.logging;

import java.util.Map;

public interface RengageLogger {

    void info(String message);
    void info(Map<String, String> arg, String message);
    void info(String methodName, String message);
    void info(String methodName, Map<String, String> arg, String message);

    void error(String message, Throwable throwable);
    void error(Map<String, String> arg, String message, Throwable throwable);
    void error(String methodName,String message, Throwable throwable);
    void error(String methodName, Map<String, String> arg, String message, Throwable throwable);

    void debug(String message);
    void debug(Map<String, String> arg, String message);
    void debug(String methodName, String message);
    void debug(String methodName, Map<String, String> arg, String message);

    void warn(String message);
    void warn(Map<String, String> arg, String message);
    void warn(String methodName, String message);
    void warn(String methodName, Map<String, String> arg, String message);
}
