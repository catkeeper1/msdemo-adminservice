package org.ckr.msdemo.adminservice.apitest.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.ckr.msdemo.exception.client.ExceptionDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
