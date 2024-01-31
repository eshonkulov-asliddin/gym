package dev.gym.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    SecurityContextImpl securityContext;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void givenValidAuthorizationHeader_doFilterInternal_shouldAuthenticateUser() throws ServletException, IOException {
        // Mocking header with a valid token
        when(request.getHeader("Authorization")).thenReturn("Bearer validToken");

        // Mocking JWTUtil behavior
        when(jwtUtil.getUsernameFromToken("validToken")).thenReturn("testUser");
        when(jwtUtil.validateToken(any(), any())).thenReturn(true);

        // Mocking UserDetailsService behavior
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        // Assert SecurityContextHolder is empty
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Execute the filter
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that SecurityContextHolder is updated
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());

        // Verify that filterChain.doFilter is called
        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void givenInvalidAuthorizationHeader_doFilterInternal_shouldNotAuthenticateUser() throws ServletException, IOException {
        // Mocking header with an invalid token
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidToken");

        // Mocking JWTUtil behavior
        when(jwtUtil.getUsernameFromToken("invalidToken")).thenReturn("testUser");
        when(jwtUtil.validateToken(any(), any())).thenReturn(false);

        // Mocking UserDetailsService behavior
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);

        // Assert SecurityContextHolder is empty
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Execute the filter
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Verify that SecurityContextHolder is not updated
        assertNull(SecurityContextHolder.getContext().getAuthentication());

        // Verify that filterChain.doFilter is called
        verify(filterChain, times(1)).doFilter(request, response);
    }
}
