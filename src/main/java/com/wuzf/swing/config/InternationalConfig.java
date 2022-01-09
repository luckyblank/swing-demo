package com.wuzf.swing.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @author lucky
 */

@Configuration
@Slf4j
public class InternationalConfig {

    @Value(value = "${spring.language.basename}")
    private String basename;

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        log.info("basename:>>"+basename);
         messageSource.setBasename(basename);
        //messageSource.setBasename("language/swingset");
        return messageSource;
    }

}
