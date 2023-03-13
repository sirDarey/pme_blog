package com.pme.token.repo;

import com.pme.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
    @Query(nativeQuery = true,
            value = "SELECT expires_at FROM tokens WHERE factor=?1 AND otp=?2")
    LocalDateTime findByFactorAndOTP(String factor, String otp);

    void deleteByFactor(String email);
}
