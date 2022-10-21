package com.alexander.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "verification_token")
public class VerificationToken extends AbstractToken {

    public VerificationToken() {
        super();
    }

    public VerificationToken(final String token) {
        super(token);
    }

    public VerificationToken(final String token, final User user) {
        super(token, user);
    }

}
