package com.xatal.psychologic.repositories;

import com.xatal.psychologic.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	Optional<Usuario> findByEmailAndPassword(String email, String password);

	Optional<Usuario> findByEmailAndPasswordAndActiveTrue(String email, String password);
}
