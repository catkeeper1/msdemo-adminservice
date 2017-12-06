package org.ckr.msdemo.adminservice.apitest.config;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.ckr.msdemo.exception.client.ExceptionDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by Administrator on 2017/8/12.
 */
@Configuration
public class FeignClientConfig {

    @Bean
    public ErrorDecoder feignClientErrorDecoder() {
        return new ExceptionDecoder();
    }

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Logger customJavaLogger() {
        return new Slf5jLogger();
    }

    public class Slf5jLogger extends feign.Logger {

        private final org.slf4j.Logger logger;

        public Slf5jLogger() {
            this(feign.Logger.class);
        }

        public Slf5jLogger(Class<?> clazz) {
            this(LoggerFactory.getLogger(clazz));
        }

        public Slf5jLogger(String name) {
            this(LoggerFactory.getLogger(name));
        }

        Slf5jLogger(org.slf4j.Logger logger) {
            this.logger = logger;
        }

        @Override
        protected void logRequest(String configKey, Level logLevel, Request request) {
                super.logRequest(configKey, logLevel, request);
        }

        @Override
        protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
                                                  long elapsedTime) throws IOException {
                return super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);

        }

        @Override
        protected void log(String configKey, String format, Object... args) {
            // Not using SLF4J's support for parameterized messages (even though it would be more efficient) because it would
            // require the incoming message formats to be SLF4J-specific.
            System.out.println("*INFO* " +
                    String.format(
                            methodTag(configKey) + format, args
                    )
            );
        }
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("bob", "bobspassword");
    }
}
