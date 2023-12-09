package dev.gym.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@NoArgsConstructor
public class User implements BaseEntity<Long> {
    private Long id; //PK
    protected String firstName;
    protected String lastName;
    protected String username;
    protected String password;
    protected boolean isActive;

    // ID generator
    private static AtomicLong idGenerator = new AtomicLong(1);

    public User(String firstName,
                String lastName,
                String username,
                String password,
                boolean isActive){
        this.id = idGenerator.getAndIncrement();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }
}