package dev.gym.security.jwt;

import dev.gym.repository.model.User;
import dev.gym.security.SecurityUser;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtUtilTest {

    private static JwtUtil jwtUtil;

    @BeforeAll
    static void setUp(){
        jwtUtil = new JwtUtil();
        jwtUtil.setSECRET("2dae84f846e4f4b158a8d26681707f4338495bc7ab68151d7f7679cc5e56202dd3da0d356da007a7c28cb0b780418f4f3246769972d6feaa8f610c7d1e7ecf6a");
        jwtUtil.setEXPIRATION_TIME(86_400_000);
    }

    @Test
    @Order(1)
    void givenUsername_whenGenerateToken_thenOk(){
        String token = jwtUtil.generateToken("username");
        assertNotNull(token);
    }

    @Test
    @Order(2)
    void givenToken_whenGetUsernameFromToken_thenOk(){
        String token = jwtUtil.generateToken("username");
        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("username", username);
    }

    @Test
    @Order(3)
    void givenToken_whenGetExpirationDateFromToken_thenOk() {
        String token = jwtUtil.generateToken("username");
        assertNotNull(jwtUtil.getExpirationDateFromToken(token));
    }

    @Test
    @Order(4)
    void givenValidToken_whenValidate_thenReturnTrue() {
        String token = jwtUtil.generateToken("username");
        User user = new User();
        user.setUsername("username");
        boolean isValid = jwtUtil.validateToken(token, new SecurityUser(user));
        assertTrue(isValid);
    }

    @Test
    @Order(5)
    void givenExpiredToken_whenValidate_thenReturnExpiredJwtException() {
        // manually set expiration time to 0 second
        jwtUtil.setEXPIRATION_TIME(1);
        String token = jwtUtil.generateToken("username");
        User user = new User();
        user.setUsername("username");
        assertThrows(ExpiredJwtException.class, () -> jwtUtil.validateToken(token, new SecurityUser(user)));
    }
}
