package com.ctorres.pokequiz.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class I18nConfig {

  final private String BUNDLE_PATH = "messages/questions";
  final private String DEFAULT_ENCODING = "UTF-8";

  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasenames(BUNDLE_PATH);
    ms.setDefaultEncoding(DEFAULT_ENCODING);
    ms.setFallbackToSystemLocale(false);
    return ms;
  }
}
