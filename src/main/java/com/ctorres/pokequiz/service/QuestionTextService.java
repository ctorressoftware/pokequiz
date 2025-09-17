package com.ctorres.pokequiz.service;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Random;

@Service
public class QuestionTextService {

  private static final int SAFE_MAX_VARIANTS = 20;
  private final MessageSource messageSource;
  private final Random random;

  public QuestionTextService(MessageSource messageSource, Random random) {
    this.messageSource = messageSource;
    this.random = random;
  }

  public String getRandomText(String baseKey, Locale locale, Object... args) {
    int maxVariant = getMaxVariant(baseKey, locale);
    if (maxVariant == 0) {
      return messageSource.getMessage(baseKey, args, locale);
    }
    int index = (maxVariant == 1) ? 1 : random.nextInt(maxVariant) + 1;
    String key = baseKey + ".v" + index;
    return messageSource.getMessage(key, args, locale);
  }

  public String getText(String key, Locale locale, Object... args) {
    return messageSource.getMessage(key, args, locale);
  }

  private int getMaxVariant(String baseKey, Locale locale) {
    int i = 1;
    while (i <= SAFE_MAX_VARIANTS) {
      try {
        messageSource.getMessage(baseKey + ".v" + i, null, locale);
        i++;
      } catch (NoSuchMessageException e) {
        return i - 1;
      }
    }
    return SAFE_MAX_VARIANTS;
  }
}