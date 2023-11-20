package dev.gym.model;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Specialization{
    private Long id;
    private String specializationTypeName;

    // ID generator
    private static AtomicLong idGenerator = new AtomicLong(1);

    public Specialization(String specializationTypeName){
        this.id = idGenerator.getAndIncrement();
        this.specializationTypeName = specializationTypeName;
    }
}
