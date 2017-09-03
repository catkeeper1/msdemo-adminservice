package org.ckr.msdemo.adminservice.apitest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/8.
 */

@SpringBootApplication(scanBasePackages = "org.ckr.msdemo.adminservice")
@EnableFeignClients(basePackages = "org.ckr.msdemo.adminservice")
public class BeanContainer {

    private static ConfigurableApplicationContext applicationContext = null;


    static {
        SpringApplication app = new SpringApplication(BeanContainer.class);
        app.setWebEnvironment(false);

        applicationContext = app.run(new String[] {});
    }

    /**
     * retrieve bean according to its type.
     *
     * @param clazz Class
     * @return Object
     */
    public static Object getBean(Class clazz) {
        Map map = applicationContext.getBeansOfType(clazz);

        return map.values().iterator().next();
    }


}
