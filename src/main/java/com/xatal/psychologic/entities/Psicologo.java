package com.xatal.psychologic.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@DiscriminatorValue("Psicologo")
public class Psicologo extends Usuario {
    public Psicologo(String username, String email, String password) {
        super(username, email, password);
    }
}
