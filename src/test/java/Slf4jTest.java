import ai.rengage.logging.LogService;
import org.junit.Test;

public class Slf4jTest {
    private LogService logService = new LogService();
    @Test
    public void test01(){
        //日志输出
        logService.info("error");
//        logService.warn("wring");
        logService.info("info");
//        logService.debug("debug");
//        logService.trace("trance");

    }

}