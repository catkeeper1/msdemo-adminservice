package org.ckr.msdemo.adminservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * This is the Application class for the admin service application.
 */
@SpringBootApplication()
public class AdminServiceApplication {

    /**
     * The entry point for the admin service application.
     * @param args Parameters for spring boot application initialization.
     */
    public static void main(String[] args) {

        new SpringApplicationBuilder(AdminServiceApplication.class)
                .properties("spring.config.name=admin_service").run(args);

    }
}
