# description
this a logger component

# use 

other service only add Dependency

```implementation 'com.github.wuzhuhua:Logger:630503be55'```

```and add 'service.name' properties in  application.properties config file ```
```
RengageLogService logger = RengageLoggerFactory.getLogger(your class name);

logger.info(your method name ,your Param,your logger content);
logger.debug(your method name ,your Param,your logger content);
logger.warn(your method name ,your Param,your logger content);
logger.error(your method name ,your Param,your logger content,your exception throwable);

```

