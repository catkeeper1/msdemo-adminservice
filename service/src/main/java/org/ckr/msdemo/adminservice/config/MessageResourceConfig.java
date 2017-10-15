package org.ckr.msdemo.adminservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Configuration on specifying how to get properties.
 */
@Configuration
public class MessageResourceConfig {

    /**
     *
     * @return an instance of AbstractMessageSource that include all message properties files.
     */
    @Bean
    public AbstractMessageSource getMessageSource() {

        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        messageSource.setBasenames("classpath:/messages/SysMessage",
                                   "classpath:/messages/SecurityMessage");

        messageSource.setCacheSeconds(5);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

}
