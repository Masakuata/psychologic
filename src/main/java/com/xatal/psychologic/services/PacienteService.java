package com.xatal.psychologic.services;

import com.xatal.psychologic.entities.Paciente;
import com.xatal.psychologic.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

	public Paciente getPacienteById(long id) {
		Optional<Paciente> paciente = pacienteRepository.findById(id);
		return paciente.orElse(null);
	}

	public int deletePaciente(long id) {
		return pacienteRepository.setPacienteInactiveById(id);
	}
}
