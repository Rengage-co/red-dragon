package ai.rengage.logging;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


class RengageLogServiceTest {
    private RengageLogger mockLogger;

    private BusinessService businessService;

    private Helpers helpers;


    @BeforeEach
    void setUp() {
        mockLogger = Mockito.mock(RengageLogger.class);
        helpers = Mockito.mock(Helpers.class);
        businessService = new BusinessService(mockLogger,helpers);
    }

    @Test
    void testInfo() {
        Map<String, String> arg = new HashMap<>();
        arg.put("key", "111");
        businessService.logError("testMethod", arg, "Test message");
        verify(mockLogger).error(
                eq("testMethod"),
                eq(arg),
                eq("Test message"),
                argThat(throwable ->
                        throwable instanceof RuntimeException
                                && "Test exception".equals(throwable.getMessage())
                )
        );
        verify(mockLogger).error(eq("testMethod"), eq(arg), eq("Test message"), any(RuntimeException.class));
    }
    @Test
    void testDoBusiness() {
        Map<String, String> arg = new HashMap<>();
        arg.put("key", "111");
        businessService.doBusiness();

        verify(mockLogger).info("doBusiness", arg, "Starting business operation");
        verify(mockLogger).info("doBusiness", arg, "Business operation completed");
    }

    @Test
    void testDoBusiness2() {
        Map<String, String> arg = new HashMap<>();
        arg.put("parm", "test");
        businessService.doBusiness2("test");
        verify(mockLogger).warn("doBusiness2", arg, "invaild parameter");

        arg.put("parm", "aaa");
        businessService.doBusiness2("aaa");
        verify(mockLogger).info("doBusiness2", arg, "Starting business operation");
        verify(mockLogger).info("doBusiness2", arg, "Business operation completed");

        when(helpers.test2(any())).thenReturn(false);
        String result = businessService.doBusiness2("aaa");

        assertEquals(result,"happyworld");
        when(helpers.test2(any())).thenReturn(true);

        result = businessService.doBusiness2("aaa");
        assertEquals(result,"sadworld");


    }

    @Test
    public void testInfo2() {
        // 准备
        RengageLogService rengageLogService = new RengageLogService();
        Map<String, String> arg = new HashMap<>();
        arg.put("key1", "value1");
        arg.put("key2", "value2");

        // 执行
        rengageLogService.info("testMethod", arg, "这是一条信息日志");

        // 验证
        verify(mockLogger).info("doBusiness2", arg, "Business operation completed");
    }

    public static void main(String[] args) {
        RengageLogService rengageLogService = new RengageLogService();
        Map<String, String> arg = new HashMap<>();
        arg.put("key1", "value1");
        arg.put("key2", "value2");
        try {
            int i = 1/0;
        }catch (Exception e){
            rengageLogService.error(arg,"这是一条信息日志",e);
        }

    }

}

