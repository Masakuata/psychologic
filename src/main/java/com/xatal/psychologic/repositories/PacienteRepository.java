package com.xatal.psychologic.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xatal.psychologic.entities.Paciente;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Long> {
}
