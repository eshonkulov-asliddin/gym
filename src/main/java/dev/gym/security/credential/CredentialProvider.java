package dev.gym.security.credential;

import dev.gym.service.exception.CredentialRetrievalException;

public interface CredentialProvider {

    String getCredential() throws CredentialRetrievalException;

}
