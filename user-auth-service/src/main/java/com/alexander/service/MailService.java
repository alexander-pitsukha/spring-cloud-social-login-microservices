package com.alexander.service;

import com.alexander.model.User;

public interface MailService {

    void sendVerificationToken(String token, User user);

}
