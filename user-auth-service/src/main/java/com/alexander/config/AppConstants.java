package com.alexander.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstants {

    public static final String TOKEN_INVALID = "INVALID";
    public static final String TOKEN_EXPIRED = "EXPIRED";
    public static final String TOKEN_VALID = "VALID";
    public static final String SUCCESS = "success";
    public static final String ID = "ID";
    public static final String USER = "USER";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MODERATOR = "ROLE_MODERATOR";
    public static final String ROLE_PRE_VERIFICATION_USER = "ROLE_PRE_VERIFICATION_USER";
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    public static final int COOKIE_EXPIRE_SECONDS = 180;
    public static final String ROLES = "roles";
    public static final String AUTHENTICATED = "authenticated";
    public static final long TEMP_TOKEN_VALIDITY_IN_MILLIS = 300000;
    public static final String SUPPORT_EMAIL = "support.email";
    public static final String LINE_BREAK = "<br>";
    public static final String BASE_URL = "baseUrl";

}
