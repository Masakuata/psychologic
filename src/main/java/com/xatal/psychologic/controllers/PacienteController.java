package com.xatal.psychologic.controllers;

import com.xatal.psychologic.entities.Paciente;
import com.xatal.psychologic.services.PacienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
	private final PacienteService pacienteService;

	@GetMapping
	public ResponseEntity<?> getPacientes() {
		return ResponseEntity.ok(pacienteService.getAllPacientes());
	}

	@PostMapping
	public ResponseEntity<?> createPaciente(@RequestBody Paciente paciente) {
		Paciente createdPaciente = pacienteService.createPaciente(paciente.getNombre(), paciente.getEmail(), paciente.getPassword());
		return new ResponseEntity<>(createdPaciente, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getPaciente(@PathVariable long id) {
		Paciente paciente = pacienteService.getPacienteById(id);
		if (paciente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(paciente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePaciente(@PathVariable long id) {
		if (pacienteService.deletePaciente(id) == 1) {
			return ResponseEntity.ok().build();
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
