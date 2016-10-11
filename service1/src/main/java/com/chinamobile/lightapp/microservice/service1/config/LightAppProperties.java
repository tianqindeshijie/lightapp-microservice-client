package com.chinamobile.lightapp.microservice.service1.config;

import de.idealo.logback.appender.RedisConnectionConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to .
 * <p>
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "baas", ignoreUnknownFields = false)
public class LightAppProperties {
    private final Logging logging = new Logging();

    /**
     * Gets logging.
     *
     * @return the logging
     */
    public Logging getLogging() { return logging; }

    /**
     * The type Logging.
     */
    public static class Logging {
        private final Redis redis = new Redis();

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        private String pattern = "{\n" +
                "\"host\": \"${HOSTNAME}\",\n" +
                "\"severity\": \"%level\",\n" +
                "\"service\": \"${springAppName:-}\",\n" +
                "\"trace\": \"%X{X-B3-TraceId:-}\",\n" +
                "\"span\": \"%X{X-B3-SpanId:-}\",\n" +
                "\"exportable\": \"%X{X-Span-Export:-}\",\n" +
                "\"pid\": \"${PID:-}\",\n" +
                "\"thread\": \"%thread\",\n" +
                "\"class\": \"%logger{40}\",\n" +
                "\"rest\": \"%message\"\n" +
                "}";
        public Redis getRedis() { return redis; }

        public static class Redis {

            private boolean enabled = true;

            private String host = "localhost";

            private int port = 6379;

            private String key = "logstash";

            private String level = "INFO";

            private RedisConnectionConfig.RedisScheme scheme = RedisConnectionConfig.RedisScheme.NODE;

            private int ringBufferSize = 131072;

            private int maxBatchMessages = 10000;

            private int maxBatchSeconds = 10;

            private String sentinelMasterName = "";

            private String sentinels = "";
            /**
             * Is enabled boolean.
             *
             * @return the boolean
             */
            public boolean isEnabled() { return enabled; }

            /**
             * Sets enabled.
             *
             * @param enabled the enabled
             */
            public void setEnabled(boolean enabled) { this.enabled = enabled; }

            /**
             * Gets host.
             *
             * @return the host
             */
            public String getHost() { return host; }

            /**
             * Sets host.
             *
             * @param host the host
             */
            public void setHost(String host) { this.host = host; }

            /**
             * Gets port.
             *
             * @return the port
             */
            public int getPort() { return port; }

            /**
             * Sets port.
             *
             * @param port the port
             */
            public void setPort(int port) { this.port = port; }

            /**
             * Gets key.
             *
             * @return the key
             */
            public String getKey() {
                return key;
            }

            /**
             * Sets key.
             *
             * @param key the key
             */
            public void setKey(String key) {
                this.key = key;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public RedisConnectionConfig.RedisScheme getScheme() {
                return scheme;
            }

            public void setScheme(RedisConnectionConfig.RedisScheme scheme) {
                this.scheme = scheme;
            }

            public int getRingBufferSize() {
                return ringBufferSize;
            }

            public void setRingBufferSize(int ringBufferSize) {
                this.ringBufferSize = ringBufferSize;
            }

            public int getMaxBatchMessages() {
                return maxBatchMessages;
            }

            public void setMaxBatchMessages(int maxBatchMessages) {
                this.maxBatchMessages = maxBatchMessages;
            }

            public int getMaxBatchSeconds() {
                return maxBatchSeconds;
            }

            public void setMaxBatchSeconds(int maxBatchSeconds) {
                this.maxBatchSeconds = maxBatchSeconds;
            }

            public String getSentinelMasterName() {
                return sentinelMasterName;
            }

            public void setSentinelMasterName(String sentinelMasterName) {
                this.sentinelMasterName = sentinelMasterName;
            }

            public String getSentinels() {
                return sentinels;
            }

            public void setSentinels(String sentinels) {
                this.sentinels = sentinels;
            }
        }
    }
}
