package com.xatal.psychologic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xatal.psychologic.entities.Paciente;
import com.xatal.psychologic.services.PacienteService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(pacienteService.getAllPacientes());
    }

    @PostMapping
    public ResponseEntity<?> createPaciente(@RequestBody Paciente paciente) {
        Paciente createdPaciente = pacienteService.createPaciente(paciente.getNombre(), paciente.getEmail(), paciente.getPassword());
        return new ResponseEntity<>(createdPaciente, HttpStatus.CREATED);
    }
}
