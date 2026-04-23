package com.xatal.psychologic.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
public class Psicologo extends User {
    public Psicologo(String username, String email, String password) {
        super(username, email, password);
    }
}
