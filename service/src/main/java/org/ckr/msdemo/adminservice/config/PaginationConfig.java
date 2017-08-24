package org.ckr.msdemo.adminservice.config;

import org.ckr.msdemo.pagination.JpaRestPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

/**
 * Created by yukai.a.lin on 8/18/2017.
 */
@Configuration
public class PaginationConfig {

    @Autowired
    EntityManager entityManager;


    @Bean
    public JpaRestPaginationService loadJpaRestPaginationService(){
        JpaRestPaginationService result = new JpaRestPaginationService();
        result.setEntityManager(this.entityManager);

        return result;
    }
}
