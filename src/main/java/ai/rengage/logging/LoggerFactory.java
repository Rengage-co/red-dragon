package ai.rengage.logging;

public class LoggerFactory {
    public static LogService getLogger(String serviceName) {
        return new LogService(serviceName);
    }
}
