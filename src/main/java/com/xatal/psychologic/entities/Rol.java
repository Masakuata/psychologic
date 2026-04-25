package com.xatal.psychologic.entities;

public enum Rol {
    PACIENTE("paciente"),
    PSICOLOGO("psicologo"),
    ADMIN("admin");

    private final String rol;

    Rol(final String rol) {
        this.rol = rol;
    }
}
