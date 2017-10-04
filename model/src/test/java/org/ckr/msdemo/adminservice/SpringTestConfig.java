package org.ckr.msdemo.adminservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

/**
 * This class is only used to init spring boot container for repository or DAO unit test case.
 * Annotation SpringBootApplication is needed for spring container initialization.
 */
@SpringBootApplication
@Import(SpringTestConfig.RegisterDaoConfig.class)
public class SpringTestConfig {

    @Configuration
    @ComponentScan(useDefaultFilters = false,
                   includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class)
                   })
    public static class RegisterDaoConfig {
    }
}
