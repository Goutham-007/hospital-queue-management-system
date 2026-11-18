package com.hqms.auth;

import com.hqms.auth.service.JwtService;
import com.hqms.user.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Encoders;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private static String newSecret() {
        return Encoders.BASE64.encode(Jwts.SIG.HS256.key().build().getEncoded());
    }

    @Test
    void generatedToken_containsUserIdEmailAndRole() {
        JwtService service = new JwtService(newSecret(), 60_000);

        Claims claims = service.parse(service.generateToken(7L, "a@b.com", Role.DOCTOR));

        assertEquals("7", claims.getSubject());
        assertEquals("a@b.com", claims.get("email", String.class));
        assertEquals("DOCTOR", claims.get("role", String.class));
    }

    @Test
    void tokenSignedWithAnotherKey_isRejected() {
        JwtService issuer = new JwtService(newSecret(), 60_000);
        JwtService verifier = new JwtService(newSecret(), 60_000);

        String forged = issuer.generateToken(1L, "a@b.com", Role.ADMIN);

        assertThrows(JwtException.class, () -> verifier.parse(forged));
    }

    @Test
    void expiredToken_isRejected() {
        JwtService service = new JwtService(newSecret(), -1_000);

        String expired = service.generateToken(1L, "a@b.com", Role.PATIENT);

        assertThrows(JwtException.class, () -> service.parse(expired));
    }
}
