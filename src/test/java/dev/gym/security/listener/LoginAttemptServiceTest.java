package dev.gym.security.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginAttemptServiceTest {

    private LoginAttemptService loginAttemptService;

    @BeforeEach
    void setUp() {
        String IP_ADDRESS = "192.168.1.1";
        loginAttemptService = new LoginAttemptService();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Forwarded-For", IP_ADDRESS);
        loginAttemptService.setRequest(request);
    }

    @Test
    void loginFailed_increaseAttempts() {
        String IP_ADDRESS = loginAttemptService.getClientIP();
        loginAttemptService.loginFailed(IP_ADDRESS);
        int attempts = loginAttemptService.getAttemptsCache().getUnchecked(IP_ADDRESS);
        assertEquals(1, attempts);
    }

    @Test
    void isBlocked_whenAttemptsExceedMaxAttempt_returnTrue() {
        String IP_ADDRESS = loginAttemptService.getClientIP();
        // Simulate login failures
        for (int i = 0; i < 3; i++) {
            loginAttemptService.loginFailed(IP_ADDRESS);
        }

        assertTrue(loginAttemptService.isBlocked());
    }

    @Test
    void isBlocked_whenAttemptsDoNotExceedMaxAttempt_returnFalse() {
        String IP_ADDRESS = loginAttemptService.getClientIP();
        // Simulate login failures less than MAX_ATTEMPT
        for (int i = 0; i < 3 - 1; i++) {
            loginAttemptService.loginFailed(IP_ADDRESS);
        }

        assertFalse(loginAttemptService.isBlocked());
    }

    @Test
    void getClientIP_fromXForwardedForHeader_returnCorrectIP() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("X-Forwarded-For", "192.168.1.1, 192.168.1.2, 192.168.1.3");
        loginAttemptService.setRequest(request);

        String clientIP = loginAttemptService.getClientIP();

        assertEquals("192.168.1.1", clientIP);
    }

    @Test
    void getClientIP_whenXForwardedForHeaderIsNull_returnRemoteAddr() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.removeHeader("X-Forwarded-For");
        request.setRemoteAddr("192.168.1.2");
        loginAttemptService.setRequest(request);

        String clientIP = loginAttemptService.getClientIP();

        assertEquals("192.168.1.2", clientIP);
    }
}
