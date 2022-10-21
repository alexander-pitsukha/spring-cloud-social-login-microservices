package com.alexander.service.impl;

import java.util.Locale;

import com.alexander.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final Locale locale = LocaleContextHolder.getLocale();

    private final MessageSource messageSource;

    public String getMessage(String code) {
        return messageSource.getMessage(code, null, locale);
    }

    public String getMessage(String code, Object... params) {
        return messageSource.getMessage(code, params, locale);
    }

}
