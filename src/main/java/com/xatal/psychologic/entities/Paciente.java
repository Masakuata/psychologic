package com.xatal.psychologic.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@DiscriminatorValue("paciente")
public class Paciente extends Usuario {
    public Paciente(String nombre, String email, String password) {
        super(nombre, email, password);
    }
}
