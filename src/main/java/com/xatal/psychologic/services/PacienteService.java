package com.xatal.psychologic.services;

import org.springframework.stereotype.Service;

import com.xatal.psychologic.entities.Paciente;
import com.xatal.psychologic.repositories.PacienteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public Iterable<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente createPaciente(String username, String email, String password) {
        return pacienteRepository.save(new Paciente(username, email, password));
    }
}
