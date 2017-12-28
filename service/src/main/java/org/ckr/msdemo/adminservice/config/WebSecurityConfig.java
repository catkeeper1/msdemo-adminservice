package org.ckr.msdemo.adminservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


/**
 * Created by Administrator on 2017/9/3.
 */


//@Configuration
//@EnableJWTTokenAuthentication
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig {
//
//
//    @Bean
//    public SecurityEvaluatorService securityEvaluatorService() {
//        return new SecurityEvaluatorService();
//    }
//
//
//    @Bean
//    public ResourceServerCustomizedSecurityConfigurer resourceServerSecurityConfigurer() {
//
//        return new ResourceServerCustomizedSecurityConfigurer() {
//
//            @Override
//            public void configure(HttpSecurity http) throws Exception {
//                http.authorizeRequests()
//                    //for swagger UI, just by pass security checking.
//                    .antMatchers("/swagger-ui.html",
//                                 "/swagger-resources",
//                                 "/swagger-resources/**/*",
//                                 "/webjars/**/*",
//                                 "/v2/api-docs").permitAll()
//                    //for all url start with /pub(for public endpoint), by pass security checking.
//                    .antMatchers("/pub/**/*").permitAll()
//                    //for all other endpoints, cannot access until authenticated.
//                    //if you want to disable the checking for development, please change it to permitAll().
//                    .anyRequest().authenticated()
//                        .and()
//                        .formLogin().
//            }
//        };
//    }
//}
@EnableWebSecurity
public class WebSecurityConfig {

    @Configuration
    @Order(1)
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeRequests()
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
                    .anyRequest().authenticated()
                    .and().formLogin()
                    .and().httpBasic()
            ;
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .ldapAuthentication()
                    .userDnPatterns("uid={0},ou=people")
                    .groupSearchBase("ou=groups")
                    .contextSource()
                    .url("ldap://localhost:8389/dc=springframework,dc=org")
                    .and()
                    .passwordCompare()
                    //.passwordEncoder(new LdapShaPasswordEncoder())
                    .passwordEncoder(new PlaintextPasswordEncoder())
                    .passwordAttribute("userPassword");
        }
    }

}