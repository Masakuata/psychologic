package com.xatal.psychologic.controllers;

import com.xatal.psychologic.Structures.Login;
import com.xatal.psychologic.entities.Usuario;
import com.xatal.psychologic.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        Usuario usuario = usuarioService.login(login);
        if (usuario == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String jwt = usuarioService.getJWT(usuario);

        Map<String, Object> values = new HashMap<>();
        values.put("id", usuario.getId());
        values.put("email", usuario.getEmail());
        values.put("jwt", jwt);

        return new ResponseEntity<>(values, HttpStatus.OK);
    }
}
