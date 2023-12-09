package dev.gym.model;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Trainee extends User {
    private Long id;
    private LocalDate dateOfBirth; // optional
    private String address; // optional

    // ID generator
    private static AtomicLong idGenerator = new AtomicLong(1);

    @Builder
    public Trainee(String firstName,
                   String lastName,
                   CredentialGenerator credentialGenerator,
                   boolean isActive,
                   LocalDate dateOfBirth,
                   String address) {
        super(firstName,
                lastName,
                credentialGenerator.generateUsername(firstName, lastName),
                credentialGenerator.generatePassword(),
                isActive);

        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.id = idGenerator.getAndIncrement();
    }
}
