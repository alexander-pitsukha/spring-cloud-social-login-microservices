package com.alexander.service;

public interface MessageService {

    String getMessage(String code);

    String getMessage(String code, Object... params);

}
