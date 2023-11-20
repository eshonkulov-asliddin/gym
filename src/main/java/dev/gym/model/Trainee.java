package dev.gym.model;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Trainee extends User {
    private Long id;
    private LocalDate dateOfBirth; // optional
    private String address; // optional

    // ID generator
    private static AtomicLong idGenerator = new AtomicLong(1);

    private Trainee(Builder builder,
                    String username,
                    String password){
        super(
            builder.firstName,
            builder.lastName,
            username,
            password,
            builder.isActive
        );

        // Validate dateOfBirth and address
        if (builder.dateOfBirth != null) {
            this.dateOfBirth = builder.dateOfBirth;
        }

        if (builder.address != null && !builder.address.trim().isEmpty()) {
            this.address = builder.address;
        }

        this.id = idGenerator.getAndIncrement();
    }
    public static class Builder {
        private final String firstName;
        private final String lastName;
        private final CredentialGenerator credentialGenerator;
        private final boolean isActive;
        private LocalDate dateOfBirth;
        private String address;

        public Builder(String firstName,
                       String lastName,
                       CredentialGenerator credentialGenerator,
                       boolean isActive){
            this.firstName = firstName;
            this.lastName = lastName;
            this.credentialGenerator = credentialGenerator;
            this.isActive = isActive;
        }

        public Builder dateOfBirth(LocalDate dateOfBirth){
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder address(String address){
            this.address = address;
            return this;
        }

        public Trainee build(){
            String username = credentialGenerator.generateUsername(firstName, lastName);
            String password = credentialGenerator.generatePassword();

            return new Trainee(this, username, password);
        }
    }
}
