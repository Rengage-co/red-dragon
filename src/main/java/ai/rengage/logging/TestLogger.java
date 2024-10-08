package ai.rengage.logging;

import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

public class TestLogger {

    public static void main(String[] args) {
        LogService logger = LoggerFactory.getLogger("TestLogger");
        Map<String, String> arg = new HashMap<>();
        arg.put("github_stars", "4000");
        arg.put("forks", "2800");
        arg.put("browser", "Chrome");
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        logger.info(methodName,arg,"This is an info message");
//        logger.warn("This is a warning message");
//        try {
//            Integer i = null;
//            Integer b = null;
//            System.out.println(i/b);
//        }catch (Exception e){
//            logger.error("This is an error message", e);
//
//        }
//        logger.debug("This is a debug message");
    }
}
