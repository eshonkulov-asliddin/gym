package dev.gym.client.workload;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class BearerTokenRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(BearerTokenRequestInterceptor.class);

    private static final String AUTHORIZATION_HEADER="Authorization";
    private static final String TOKEN_TYPE = "Bearer";

    @Override
    public void apply(RequestTemplate template) {
        logger.info("Adding Bearer token to the request");

        // Get the authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getCredentials() != null) {
            logger.info("Bearer token found in SecurityContextHolder");
            // Extract the bearer token
            String token = authentication.getCredentials().toString();

            // Add the Authorization header with the bearer token
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", TOKEN_TYPE, token));
        }
    }
}
