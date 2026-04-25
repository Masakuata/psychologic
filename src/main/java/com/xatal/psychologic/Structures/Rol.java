package com.xatal.psychologic.Structures;

public enum Rol {
    PACIENTE("paciente"),
    PSICOLOGO("psicologo"),
    ADMIN("admin");

    private final String rol;

    Rol(final String rol) {
        this.rol = rol;
    }
}
