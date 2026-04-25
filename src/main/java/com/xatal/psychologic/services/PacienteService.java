package com.xatal.psychologic.services;

import com.xatal.psychologic.entities.Paciente;
import com.xatal.psychologic.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PacienteService {
    private final UsuarioService usuarioService;
    private final PacienteRepository pacienteRepository;

    public Iterable<Paciente> getAllPacientes() {
        return pacienteRepository.findAll();
    }

    public Paciente createPaciente(String username, String email, String password) {
        password = usuarioService.encodePassword(password);
        return pacienteRepository.save(new Paciente(username, email, password));
    }
}
