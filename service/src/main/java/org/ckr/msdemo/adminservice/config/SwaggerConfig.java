package org.ckr.msdemo.adminservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This is the configuration for swagger UI.
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     * This bean create a bean that can customize swagger UI content.
     * @return an instance of Docket.
     */
    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.ckr.msdemo.adminservice.controller"))
                .build();

    }

}
