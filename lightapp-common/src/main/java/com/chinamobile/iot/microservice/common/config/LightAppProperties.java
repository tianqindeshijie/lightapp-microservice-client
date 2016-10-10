package com.chinamobile.iot.microservice.common.config;

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
        }
    }
}
