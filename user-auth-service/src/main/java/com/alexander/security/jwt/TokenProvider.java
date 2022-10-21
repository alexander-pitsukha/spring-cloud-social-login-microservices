package com.alexander.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.alexander.config.AppProperties;
import com.alexander.dto.LocalUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import static com.alexander.config.AppConstants.AUTHENTICATED;
import static com.alexander.config.AppConstants.ROLES;
import static com.alexander.config.AppConstants.TEMP_TOKEN_VALIDITY_IN_MILLIS;

@Service
@Slf4j
public class TokenProvider {

    private final AppProperties appProperties;
    private final Key key;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
        this.key = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());
    }

    public String createToken(LocalUser userPrincipal, boolean authenticated) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()
                + (authenticated ? appProperties.getAuth().getTokenExpirationMsec() : TEMP_TOKEN_VALIDITY_IN_MILLIS));
        String roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder().setSubject(Long.toString(userPrincipal.getUser().getId())).claim(ROLES, roles)
                .claim(AUTHENTICATED, authenticated).setIssuedAt(new Date()).setExpiration(expiryDate).signWith(key)
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = parseClaims(token).getBody();
        return Long.parseLong(claims.getSubject());
    }

    private Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public Boolean isAuthenticated(String token) {
        Claims claims = parseClaims(token).getBody();
        return claims.get(AUTHENTICATED, Boolean.class);
    }

    public boolean validateToken(String authToken) {
        try {
            parseClaims(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

}
