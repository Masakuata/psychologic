package com.xatal.psychologic.services;

import com.xatal.psychologic.Structures.Login;
import com.xatal.psychologic.entities.Usuario;
import com.xatal.psychologic.repositories.UsuarioRepository;
import com.xatal.psychologic.util.TokenUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final TokenUtils tokenUtils;
	private MessageDigest digest;

	@PostConstruct
	private void init() throws NoSuchAlgorithmException {
		try {
			digest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("SHA-512 NOT AVAILABLE TO HASH PASSWORDS ------------");
			throw e;
		}
	}

	public Usuario login(Login login) {
		Optional<Usuario> optionalUsuario =
			usuarioRepository.findByEmailAndPasswordAndActiveTrue(login.email(), encodePassword(login.password()));
		return optionalUsuario.orElse(null);
	}

	public String getJWT(Usuario usuario) {
		return tokenUtils.createToken(usuario);
	}

	protected String encodePassword(String password) {
		return Base64.getEncoder().encodeToString(digest.digest(password.getBytes()));
	}
}
