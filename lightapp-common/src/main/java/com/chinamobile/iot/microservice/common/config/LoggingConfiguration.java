package com.chinamobile.iot.microservice.common.config;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import de.idealo.logback.appender.RedisBatchAppender;
import de.idealo.logback.appender.RedisConnectionConfig;
import net.logstash.logback.appender.LoggingEventAsyncDisruptorAppender;
import net.logstash.logback.composite.JsonProviders;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(LightAppProperties.class)
public class LoggingConfiguration {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String serverPort;

    @Value("${eureka.instance.instanceId}")
    private String instanceId;

    @Autowired
    private LightAppProperties lightAppProperties;


    private void init() {
        if (lightAppProperties.getLogging().getLogstash().isEnabled()) {
            addLogstashAppender();
        }
    }

    public void addLogstashAppender() {
        log.info("Initializing log-redis-logstash logging");
        //redis 连接配置
        RedisConnectionConfig redisConnectionConfig = new RedisConnectionConfig();
        redisConnectionConfig.setHost(lightAppProperties.getLogging().getLogstash().getHost());
        redisConnectionConfig.setPort(lightAppProperties.getLogging().getLogstash().getPort());
        redisConnectionConfig.setKey(lightAppProperties.getLogging().getLogstash().getKey());
        redisConnectionConfig.setScheme(RedisConnectionConfig.RedisScheme.NODE);
        RedisBatchAppender redisBatchAppender = new RedisBatchAppender();
        LoggingEventAsyncDisruptorAppender logRedisAppender = new LoggingEventAsyncDisruptorAppender();

        JsonProviders<ILoggingEvent> providers = new JsonProviders<ILoggingEvent>();
        LoggingEventCompositeJsonEncoder encoder = new LoggingEventCompositeJsonEncoder();
        encoder.getProviders().setJsonFactory();
/*        logRedisAppender.addAppender();
        LogstashSocketAppender logstashAppender = new LogstashSocketAppender();
        logstashAppender.setName("LOGSTASH");
        logstashAppender.setContext(context);
        String customFields = "{\"app_name\":\"" + appName + "\",\"app_port\":\"" + serverPort + "\"," +
            "\"instance_id\":\"" + instanceId + "\"}";

        // Set the Logstash appender config from JHipster properties
        logstashAppender.setSyslogHost(LightAppProperties.getLogging().getLogstash().getHost());
        logstashAppender.setPort(LightAppProperties.getLogging().getLogstash().getPort());
        logstashAppender.setCustomFields(customFields);

        // Limit the maximum length of the forwarded stacktrace so that it won't exceed the 8KB UDP limit of logstash
        ShortenedThrowableConverter throwableConverter = new ShortenedThrowableConverter();
        throwableConverter.setMaxLength(7500);
        throwableConverter.setRootCauseFirst(true);
        logstashAppender.setThrowableConverter(throwableConverter);

        logstashAppender.start();

        // Wrap the appender in an Async appender for performance
        AsyncAppender asyncLogstashAppender = new AsyncAppender();
        asyncLogstashAppender.setContext(context);
        asyncLogstashAppender.setName("ASYNC_LOGSTASH");
        asyncLogstashAppender.setQueueSize(LightAppProperties.getLogging().getLogstash().getQueueSize());
        asyncLogstashAppender.addAppender(logstashAppender);
        asyncLogstashAppender.start();

        context.getLogger("ROOT").addAppender(asyncLogstashAppender);*/
    }
}
