# Rengage Logger Component

A structured logging library for Java applications that provides context-aware logging with trace ID propagation and MDC support.

## Features

- **Structured Logging**: JSON-formatted logs with Logstash encoder
- **Context Propagation**: Automatic trace ID propagation using TransmittableThreadLocal
- **MDC Support**: Automatic injection of method names, class names, and custom parameters
- **Asynchronous Support**: Thread-safe logging for asynchronous operations
- **Multiple Log Levels**: info, debug, warn, and error with exception support

## Installation

Add the dependency to your project:

```gradle
implementation 'com.github.wuzhuhua:Logger:630503be55'
```

## Configuration

Add the service name property in your `application.properties`:

```properties
service.name=your-service-name
```

## Usage

### Basic Logging

```java
RengageLogService logger = RengageLoggerFactory.getLogger(YourClassName.class);

// Info level logging
logger.info("methodName", parametersMap, "Log message");

// Debug level logging  
logger.debug("methodName", parametersMap, "Debug message");

// Warning level logging
logger.warn("methodName", parametersMap, "Warning message");

// Error level logging with exception
logger.error("methodName", parametersMap, "Error message", exception);
```

### Example Implementation

```java
public String doBusiness(String parameter) {
    Map<String, String> args = new HashMap<>();
    args.put("param", parameter);
    
    if (parameter == null || parameter.equals("test")) {
        logger.warn("doBusiness", args, "Invalid parameter");
        return "error";
    }
    
    logger.info("doBusiness", args, "Starting business operation");
    
    // Business logic here
    
    logger.info("doBusiness", args, "Business operation completed");
    return "success";
}