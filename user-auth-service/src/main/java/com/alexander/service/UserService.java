package com.alexander.service;

import java.util.Map;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import com.alexander.dto.LocalUser;
import com.alexander.dto.SignUpRequest;
import com.alexander.exception.UserAlreadyExistAuthenticationException;
import com.alexander.model.User;

public interface UserService {

    User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    User findUserById(Long id);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);

    void createVerificationTokenForUser(User user, String token);

    boolean resendVerificationToken(String token);

    String validateVerificationToken(String token);

}
