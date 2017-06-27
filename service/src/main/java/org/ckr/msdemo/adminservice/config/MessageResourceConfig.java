package org.ckr.msdemo.adminservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * Created by Administrator on 2017/6/11.
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

        messageSource.setBasenames("/WEB-INF/messages/SysMessage",
                                   "/WEB-INF/messages/SecurityMessage");

        messageSource.setCacheSeconds(5);
        messageSource.setUseCodeAsDefaultMessage(true);

        return messageSource;
    }

}
