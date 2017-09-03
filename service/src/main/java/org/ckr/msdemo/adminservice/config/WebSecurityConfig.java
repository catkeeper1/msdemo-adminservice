package org.ckr.msdemo.adminservice.config;

import org.ckr.msdemo.adminservice.service.SecurityEvaluatorService;
import org.ckr.msdemo.reserver.config.EnableJWTTokenAuthentication;
import org.ckr.msdemo.reserver.config.ResourceServerConfig;
import org.ckr.msdemo.reserver.config.ResourceServerSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * Created by Administrator on 2017/9/3.
 */


@Configuration
//@Import({ResourceServerConfig.class})
//@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {


    @Bean
    public SecurityEvaluatorService securityEvaluatorService() {
        return new SecurityEvaluatorService();
    }


//    @Bean
//    public ResourceServerSecurityConfigurer resourceServerSecurityConfigurer() {
//
//        return new ResourceServerSecurityConfigurer() {
//
//            @Override
//            public void configure(HttpSecurity http) throws Exception {
//                http.authorizeRequests().anyRequest().authenticated();
//            }
//        };
//
//
//    }

}
