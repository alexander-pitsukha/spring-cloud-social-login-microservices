package com.alexander.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import com.alexander.util.GeneralUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractToken extends BaseEntity<Long> {

    private static final int EXPIRATION = 60 * 24;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    protected AbstractToken(final String token) {
        super();
        this.token = token;
        this.expiryDate = GeneralUtils.calculateExpiryDate(EXPIRATION);
    }

    protected AbstractToken(final String token, final User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = GeneralUtils.calculateExpiryDate(EXPIRATION);
    }

    public void updateToken(final String token) {
        this.token = token;
        this.expiryDate = GeneralUtils.calculateExpiryDate(EXPIRATION);
    }

}
