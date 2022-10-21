package com.alexander.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexander.model.VerificationToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    @Query("select v from VerificationToken v where v.token = :token")
    VerificationToken findByToken(@Param("token") String token);

    @Query("select v from VerificationToken v where v.user.id = :userId")
    VerificationToken findByUser(@Param("userId") Long userId);

}
