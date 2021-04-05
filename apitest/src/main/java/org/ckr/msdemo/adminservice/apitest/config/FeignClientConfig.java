package org.ckr.msdemo.adminservice.apitest.config;

import feign.FeignException;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.ckr.msdemo.exception.SystemException;
import org.ckr.msdemo.exception.client.ExceptionDecoder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;

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
    public Decoder decoder (ObjectFactory<HttpMessageConverters> messageConverters) {

        final SpringDecoder decoder = new SpringDecoder(messageConverters);

        //for decoder testing only. Should not used in production code
        return new Decoder() {
            @Override
            public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {


                String json = readJsonFromBody(response.body());

                System.out.println("decoded json: " + json);

                return decoder.decode(response, type);
            }

            private String readJsonFromBody(Response.Body body) {

                final char[] buffer = new char[1024];
                final StringBuilder out = new StringBuilder();

                try (Reader reader = new InputStreamReader(body.asInputStream(), "UTF-8")) {

                    while (true) {
                        int rsz = reader.read(buffer, 0, buffer.length);

                        if (rsz < 0) {
                            break;
                        }

                        out.append(buffer, 0, rsz);
                    }
                } catch (IOException ioException) {
                    throw new SystemException(ioException);
                }
                return out.toString();

            }
        };
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
