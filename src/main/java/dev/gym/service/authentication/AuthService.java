package dev.gym.service.authentication;

public interface AuthService<T> {

    void authenticate(String username, String password);

}