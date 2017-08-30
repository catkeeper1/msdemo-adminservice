package org.ckr.msdemo.adminservice.config;

import org.ckr.msdemo.pagination.JpaRestPaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * Configure Pagination module and init JpaRestPaginationService.
 */
@Configuration
public class PaginationConfig {

    @Autowired
    EntityManager entityManager;


    /**
     * Init JpaRestPaginationService with data source entityManager.
     *
     * @return JpaRestPaginationService
     */
    @Bean
    public JpaRestPaginationService loadJpaRestPaginationService() {
        JpaRestPaginationService result = new JpaRestPaginationService();
        result.setEntityManager(this.entityManager);

        return result;
    }
}
