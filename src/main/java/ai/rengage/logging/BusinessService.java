package ai.rengage.logging;

import java.util.HashMap;
import java.util.Map;

public class BusinessService {
    private final RengageLogger logger;

    private final Helpers helpers;
    public BusinessService(RengageLogger logger,Helpers helpers) {
        this.logger = logger;
        this.helpers = helpers;
    }

    public void logInfo(String method, Map<String, String> args, String message) {
        logger.info(method, args, message);
    }

    public void logError(String method, Map<String, String> args, String message) {
        logger.error(method, args, message,new RuntimeException("Test exception"));
    }

    public void doBusiness() {
        // business logic

        Map<String, String> arg = new HashMap<>();
        arg.put("key", "111");
        logger.info("doBusiness", arg, "Starting business operation");
        // 业务逻辑
        logger.info("doBusiness", arg, "Business operation completed");
        //sync end

        // async

    }


    public String doBusiness2(String s) {
        // business logic

        Map<String, String> arg = new HashMap<>();
        arg.put("parm", s);
        if (s== null || s.equals("test")) {
            logger.warn("doBusiness2", arg, "invaild parameter");
            return "error";
        }

        logger.info("doBusiness2", arg, "Starting business operation");
        // 业务逻辑
        String s1 = "hello";
        String s2 = "world";
        String result = s1 + s2;
        // async
        new Thread(() -> {
            testAsync(result);
        }).start();
        logger.info("doBusiness2", arg, "Business operation completed");
        try {

        }catch (Exception e){
            logger.error("doBusiness2", arg, "Business operation completed", new RuntimeException("test"));

        }
        if (!helpers.test2(result)){
            s1 = "happy";
        }else {
            s1 = "sad";
        }
        return s1+s2;


        //sync end

    }

    private void testAsync(String result) {
        Map<String, String> arg = new HashMap<>();
        arg.put("result", result);
        logger.info("testAsync", arg, "Business operation completed");
    }
}
