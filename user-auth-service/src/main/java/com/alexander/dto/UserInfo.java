package com.alexander.dto;

import java.util.List;

import lombok.Value;

@Value
public class UserInfo {

    String id;

    String displayName;

    String email;

    List<String> roles;

}
