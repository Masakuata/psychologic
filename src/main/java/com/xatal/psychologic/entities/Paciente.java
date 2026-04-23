package com.xatal.psychologic.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
public class Paciente extends User {
    public Paciente(String nombre, String email, String password) {
        super(nombre, email, password);
    }
}
