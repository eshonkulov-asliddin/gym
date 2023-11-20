package dev.gym.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class User implements BaseEntity<Long> {
    protected Long userId; //PK
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
        this.userId = idGenerator.getAndIncrement();
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    @Override
    public Long getId() {
        return userId;
    }

    @Override
    public void setId(Long id) {
        this.userId = id;
    }
}