package com.alexander.controller;

import com.alexander.dto.LocalUser;
import com.alexander.dto.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.config.CurrentUser;
import com.alexander.util.GeneralUtils;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping("me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserInfo> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("all")
    public ResponseEntity<String> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @GetMapping("user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @GetMapping("mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<String> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }

}
