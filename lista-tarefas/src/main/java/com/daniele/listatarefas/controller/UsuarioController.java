package com.daniele.listatarefas.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniele.listatarefas.model.Usuario;
import com.daniele.listatarefas.service.UsuarioService;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{email}")
    public Usuario filtrarPorEmail(@PathVariable String email) {
        return usuarioService.filtrarPorEmail(email);
    }

    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable @NotNull @Positive Long id) {
        return usuarioService.buscarPorId(id);
    }
}
