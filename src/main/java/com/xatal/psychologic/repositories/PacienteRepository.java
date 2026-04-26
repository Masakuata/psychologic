package com.xatal.psychologic.repositories;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.xatal.psychologic.entities.Paciente;

@Repository
public interface PacienteRepository extends CrudRepository<Paciente, Long> {
	@Modifying
	@Transactional
	@Query(value = "UPDATE Paciente p SET p.isActive = false WHERE p.id = :id")
	int setPacienteInactiveById(long id);
}
