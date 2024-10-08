package ai.rengage.logging;

public class RengageLoggerFactory {
    public static RengageLogService getLogger(String serviceName) {
        return new RengageLogService(serviceName);
    }
}
