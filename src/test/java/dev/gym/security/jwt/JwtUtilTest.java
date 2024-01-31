package dev.gym.security.jwt;

import dev.gym.repository.model.User;
import dev.gym.security.SecurityUser;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String token;

    @BeforeEach
     void setUp(){
        jwtUtil = new JwtUtil();
        jwtUtil.setSecret("2dae84f846e4f4b158a8d26681707f4338495bc7ab68151d7f7679cc5e56202dd3da0d356da007a7c28cb0b780418f4f3246769972d6feaa8f610c7d1e7ecf6a");
        jwtUtil.setExpirationTime(3600); // 1 hour
        token = jwtUtil.generateToken("username");
    }

    @Test
    void givenUsername_whenGenerateToken_thenOk(){
        assertNotNull(token);
    }

    @Test
    void givenToken_whenGetUsernameFromToken_thenOk(){
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("username", username);
    }

    @Test
    void givenToken_whenGetExpirationDateFromToken_thenOk() {
        assertNotNull(jwtUtil.getExpirationDateFromToken(token));
    }

    @Test
    void givenValidToken_whenValidate_thenReturnTrue() {
        User user = new User();
        user.setUsername("username");
        boolean isValid = jwtUtil.validateToken(token, new SecurityUser(user));
        assertTrue(isValid);
    }

    @Test
    void givenExpiredToken_whenValidate_thenReturnExpiredJwtException() {
        // manually set expiration time to 0 second
        jwtUtil.setExpirationTime(0);
        token = jwtUtil.generateToken("username");
        User user = new User();
        user.setUsername("username");
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(token, new SecurityUser(user)));
    }
}
