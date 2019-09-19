package org.ckr.msdemo.adminservice.config;

import org.ckr.msdemo.exception.valueobject.ErrorResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import com.fasterxml.classmate.TypeResolver;

import java.util.ArrayList;
import java.util.List;

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
    public Docket apiDocket() {

        List list = new ArrayList<>();

        ResponseMessage resMsg = new ResponseMessageBuilder()
                .code(500)
                .message("Global exception message")
                .responseModel(new ModelRef("ErrorResponse"))
                .build();

        list.add(resMsg);

        TypeResolver typeResolver = new TypeResolver();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                    .apis(RequestHandlerSelectors.basePackage("org.ckr.msdemo.adminservice.controller"))
                    .build()
                .globalResponseMessage(RequestMethod.GET, list)
                .globalResponseMessage(RequestMethod.POST, list)
                .globalResponseMessage(RequestMethod.PUT, list)
                .globalResponseMessage(RequestMethod.DELETE, list)
                .additionalModels(typeResolver.resolve(ErrorResponse.class))
                .globalOperationParameters(
                        newArrayList(new ParameterBuilder()
                                .name("Authorization")
                                .description("OAuth2 token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()))
                .useDefaultResponseMessages(true);

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("MSDEMO admin service APIs")
                .version("1.0.0")

                .build();
    }

}
