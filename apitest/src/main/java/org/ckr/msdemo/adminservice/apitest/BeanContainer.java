package org.ckr.msdemo.adminservice.apitest;

import feign.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/8.
 */

@SpringBootApplication(scanBasePackages = "org.ckr.msdemo.adminservice")
@EnableFeignClients(basePackages ="org.ckr.msdemo.adminservice")
public class BeanContainer {

    private static ConfigurableApplicationContext applicationContext = null;


    private static ComponentA componentA;

    @Autowired
    public void setComponentA(ComponentA c) {
        componentA = c;
    }

    static {
        SpringApplication app = new SpringApplication(BeanContainer.class);
        app.setWebEnvironment(false);

        applicationContext = app.run(new String[]{});
    }

    public static Object getBean(Class clazz) {
        Map map = applicationContext.getBeansOfType(clazz);

        return map.values().iterator().next();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
