package org.ckr.msdemo.adminservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

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
                    .build()
                .globalOperationParameters(
                        newArrayList(new ParameterBuilder()
                                .name("Authorization")
                                .description("OAuth2 token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()));

    }

}
