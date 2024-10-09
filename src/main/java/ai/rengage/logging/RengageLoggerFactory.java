package ai.rengage.logging;

public class RengageLoggerFactory {
    public static RengageLogService getLogger(String className) {
        return new RengageLogService(className);
    }
}
