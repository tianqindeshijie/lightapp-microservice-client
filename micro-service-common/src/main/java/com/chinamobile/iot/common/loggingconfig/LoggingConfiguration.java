package com.chinamobile.iot.common.loggingconfig;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.encoder.Encoder;
import de.idealo.logback.appender.RedisBatchAppender;
import de.idealo.logback.appender.RedisConnectionConfig;
import net.logstash.logback.appender.LoggingEventAsyncDisruptorAppender;
import net.logstash.logback.composite.loggingevent.LoggingEventJsonProviders;
import net.logstash.logback.composite.loggingevent.LoggingEventPatternJsonProvider;
import net.logstash.logback.composite.loggingevent.StackTraceJsonProvider;
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder;
import net.logstash.logback.stacktrace.ShortenedThrowableConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@RefreshScope
@Configuration
@EnableConfigurationProperties(LightAppProperties.class)
public class LoggingConfiguration {

    private final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    private LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

    @Inject
    private LightAppProperties lightAppProperties;

    @PostConstruct
    private void init() {
        addRedisAppender();
    }
    public void addRedisAppender() {
        log.info("Initializing log-redis-logstash logging");
        //1. 初始化最顶层的appender
        LoggingEventAsyncDisruptorAppender logAppender = new LoggingEventAsyncDisruptorAppender();
        //2. 设置缓冲池大小（ringBufferSize），如果太小可能出现日志丢失情况
        logAppender.setRingBufferSize(lightAppProperties.getLogging().getRedis().getRingBufferSize());
        //3. 设置waitStrategyType ,waitStrategyType的种类参见:https://github.com/logstash/logstash-logback-encoder#async
        logAppender.setWaitStrategyType("sleeping");
        //4. 先声明并初始一个redisBatchAppender
        RedisBatchAppender redisBatchAppender = new RedisBatchAppender();
        //5. 设置redis 连接配置
        RedisConnectionConfig redisConnectionConfig = new RedisConnectionConfig();
        RedisConnectionConfig.RedisScheme scheme = lightAppProperties.getLogging().getRedis().getScheme();
        //设置redis模式
        redisConnectionConfig.setScheme(scheme);
        //设置redis消息key
        redisConnectionConfig.setKey(lightAppProperties.getLogging().getRedis().getKey());
        //判断redis是单机还是集群
        if(scheme == RedisConnectionConfig.RedisScheme.SENTINEL) {
            redisConnectionConfig.setSentinelMasterName(lightAppProperties.getLogging().getRedis().getSentinelMasterName());
            redisConnectionConfig.setSentinels(lightAppProperties.getLogging().getRedis().getSentinels());
        } else {
            //设置地址和端口
            redisConnectionConfig.setHost(lightAppProperties.getLogging().getRedis().getHost());
            redisConnectionConfig.setPort(lightAppProperties.getLogging().getRedis().getPort());
        }
        //设置redis模式
        redisConnectionConfig.setScheme(scheme);
        //设置redis消息key
        redisConnectionConfig.setKey(lightAppProperties.getLogging().getRedis().getKey());
        //判断redis是单机还是集群
        if(scheme == RedisConnectionConfig.RedisScheme.SENTINEL) {
            redisConnectionConfig.setSentinelMasterName(lightAppProperties.getLogging().getRedis().getSentinelMasterName());
            redisConnectionConfig.setSentinels(lightAppProperties.getLogging().getRedis().getSentinels());
        } else {
            //设置地址和端口
            redisConnectionConfig.setHost(lightAppProperties.getLogging().getRedis().getHost());
            redisConnectionConfig.setPort(lightAppProperties.getLogging().getRedis().getPort());
        }
        //6. 设置redisConnectionConfig
        redisBatchAppender.setConnectionConfig(redisConnectionConfig);
        //7. 设置maxBatchMessages
        redisBatchAppender.setMaxBatchMessages(lightAppProperties.getLogging().getRedis().getMaxBatchMessages());
        //8. 设置maxBatchSeconds
        redisBatchAppender.setMaxBatchSeconds(lightAppProperties.getLogging().getRedis().getMaxBatchSeconds());
        //9. 初始化一个Encoder
        LoggingEventCompositeJsonEncoder encoder = new LoggingEventCompositeJsonEncoder();
        LoggingEventJsonProviders providers = new LoggingEventJsonProviders();
        LoggingEventPatternJsonProvider patternJsonProvider = new LoggingEventPatternJsonProvider();
        patternJsonProvider.setPattern(lightAppProperties.getLogging().getPattern());
        patternJsonProvider.setContext(context);
        patternJsonProvider.start();
        providers.addPattern(patternJsonProvider);
        StackTraceJsonProvider stackTraceJsonProvider = new StackTraceJsonProvider();
        ShortenedThrowableConverter shortenedThrowableConverter = new ShortenedThrowableConverter();
        shortenedThrowableConverter.setMaxDepthPerThrowable(30);
        shortenedThrowableConverter.setMaxLength(4096);
        shortenedThrowableConverter.setShortenedClassNameLength(20);
        shortenedThrowableConverter.setRootCauseFirst(true);
        stackTraceJsonProvider.setThrowableConverter(shortenedThrowableConverter);
        stackTraceJsonProvider.setContext(context);
        stackTraceJsonProvider.start();
        providers.addStackTrace(stackTraceJsonProvider);
        providers.setContext(context);
        providers.start();
        encoder.setProviders(providers);
        encoder.setContext(context);
        encoder.start();
        redisBatchAppender.setEncoder((Encoder)encoder);
        redisBatchAppender.setContext(context);
        redisBatchAppender.setName("redis");
        redisBatchAppender.start();
        logAppender.setContext(context);
        logAppender.setName("logstash");
        logAppender.addAppender((Appender)redisBatchAppender);
        logAppender.start();
        context.getLogger("ROOT").addAppender(logAppender);
        System.out.println("success");
    }
}
