package dev.gym.model;

import dev.gym.repository.datasource.credential.CredentialGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicLong;


@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Trainer extends User {
    private Long id; //PK
    private Specialization specialization; //FK

    // ID generator
    private static AtomicLong idGenerator = new AtomicLong(1);
    public Trainer(String firstName,
                   String lastName,
                   CredentialGenerator credentialGenerator,
                   boolean isActive,
                   Specialization specialization) {
        super(firstName,
                lastName,
                credentialGenerator.generateUsername(firstName, lastName),
                credentialGenerator.generatePassword(),
                isActive);
        this.id = idGenerator.getAndIncrement();
        this.specialization = specialization;
    }
}
