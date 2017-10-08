package org.ckr.msdemo.adminservice.config;

import org.ckr.msdemo.adminservice.service.SecurityEvaluatorService;
import org.ckr.msdemo.reserver.config.EnableJWTTokenAuthentication;
import org.ckr.msdemo.reserver.config.ResourceServerCustomizedSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;



/**
 * Created by Administrator on 2017/9/3.
 */


@Configuration
@EnableJWTTokenAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {


    @Bean
    public SecurityEvaluatorService securityEvaluatorService() {
        return new SecurityEvaluatorService();
    }


    @Bean
    public ResourceServerCustomizedSecurityConfigurer resourceServerSecurityConfigurer() {

        return new ResourceServerCustomizedSecurityConfigurer() {

            @Override
            public void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests()
                    //for swagger UI, just by pass security checking.
                    .antMatchers("/swagger-ui.html",
                                 "/swagger-resources",
                                 "/swagger-resources/**/*",
                                 "/webjars/**/*",
                                 "/v2/api-docs").permitAll()
                    //for all url start with /pub(for public endpoint), by pass security checking.
                    .antMatchers("/pub/**/*").permitAll()
                    //for all other endpoints, cannot access until authenticated.
                    //if you want to disable the checking for development, please change it to permitAll().
                    .anyRequest().authenticated();
            }
        };


    }

}
