package ai.rengage.logging;

public class RengageLoggerFactory {
    public static RengageLogService getLogger() {
        return new RengageLogService();
    }
}
