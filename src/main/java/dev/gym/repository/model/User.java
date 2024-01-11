package dev.gym.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "firstname", nullable = false)
    protected String firstName;

    @Column(name = "lastname", nullable = false)
    protected String lastName;

    @Column(name = "username", nullable = false, unique = true)
    protected String username;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "is_active", nullable = false)
    protected boolean isActive;

    public void update(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.isActive = user.isActive;
    }
}
