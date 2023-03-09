package com.daniele.listatarefas.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.daniele.listatarefas.exception.RecordNotFoundException;
import com.daniele.listatarefas.model.Usuario;
import com.daniele.listatarefas.repository.UsuarioRepository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario filtrarPorEmail(String email) {
        return this.usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException());
    }

    public Usuario buscarPorId(@PathVariable @NotNull @Positive Long id) {
        return this.usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
    
}
