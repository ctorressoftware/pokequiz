package com.ctorres.pokequiz.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class I18nConfig {

    @Bean
    public MessageSource messageSource() {
        final var BUNDLE_PATH = "messages/questions";
        final var DEFAULT_ENCODING = "UTF-8";
        final var ms = new ResourceBundleMessageSource();
        ms.setBasenames(BUNDLE_PATH);
        ms.setDefaultEncoding(DEFAULT_ENCODING);
        ms.setFallbackToSystemLocale(false);
        return ms;
    }
}
