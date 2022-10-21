package com.alexander.dto;

import java.util.Map;
import java.util.Objects;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.alexander.util.GeneralUtils;

public class LocalUser extends User implements OAuth2User, OidcUser {

    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;
    private transient Map<String, Object> attributes;
    private final com.alexander.model.User user;

    public LocalUser(LocalUserBuilder builder) {
        super(builder.getUserId(), builder.getPassword(), builder.isEnabled(), builder.isAccountNonExpired(), builder.isCredentialsNonExpired(),
                builder.isAccountNonLocked(), builder.getAuthorities());
        this.user = builder.getUser();
        this.idToken = builder.getIdToken();
        this.userInfo = builder.getUserInfo();
    }

    public static LocalUser create(com.alexander.model.User user, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo) {
        LocalUserBuilder builder = LocalUserBuilder.builder().userId(user.getEmail()).password(user.getPassword()).enabled(user.isEnabled()).accountNonExpired(true)
                .credentialsNonExpired(true).accountNonLocked(true).authorities(GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()))
                .user(user).idToken(idToken).userInfo(userInfo).build();
        LocalUser localUser = new LocalUser(builder);
        localUser.setAttributes(attributes);
        return localUser;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.user.getDisplayName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    public com.alexander.model.User getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        LocalUser localUser = (LocalUser) o;
        return Objects.equals(idToken, localUser.idToken) && Objects.equals(userInfo, localUser.userInfo) && Objects.equals(attributes, localUser.attributes) && Objects.equals(user, localUser.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idToken, userInfo, attributes, user);
    }

    public static LocalUser createLocalUser(com.alexander.model.User user) {
        LocalUserBuilder builder = LocalUserBuilder.builder().userId(user.getEmail()).password(user.getPassword()).enabled(user.isEnabled()).accountNonExpired(true)
                .credentialsNonExpired(true).accountNonLocked(true).authorities(GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles())).user(user).build();
        return new LocalUser(builder);
    }

}
