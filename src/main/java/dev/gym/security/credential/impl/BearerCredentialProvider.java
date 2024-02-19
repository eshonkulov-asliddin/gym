package dev.gym.security.credential.impl;

import dev.gym.security.credential.CredentialProvider;
import dev.gym.service.exception.CredentialRetrievalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class BearerCredentialProvider implements CredentialProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(BearerCredentialProvider.class);
    private static final String AUTH_TYPE = "Bearer ";

    @Override
    public String getCredential() throws CredentialRetrievalException {
        LOGGER.info("Retrieving current auth user's credentials");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getCredentials() != null) {
            return AUTH_TYPE + authentication.getCredentials().toString();
        }

        LOGGER.error("Error retrieving current auth user's credentials");
        throw new CredentialRetrievalException("Error retrieving current auth user's credentials");
    }
}
