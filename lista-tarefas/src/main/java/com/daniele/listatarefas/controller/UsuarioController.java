package com.daniele.listatarefas.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daniele.listatarefas.dto.UsuarioDTO;
import com.daniele.listatarefas.model.Usuario;
import com.daniele.listatarefas.service.UsuarioService;

import jakarta.validation.Valid;
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

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping("/email/{email}")
    public Usuario filtrarPorEmail(@PathVariable String email) {
        return usuarioService.filtrarPorEmail(email);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping("/{id}")
    public Usuario buscarPorId(@PathVariable @NotNull @Positive Long id) {
        return usuarioService.buscarPorId(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @PostMapping()
    public Usuario salvar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.salvar(usuarioDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @PutMapping("/{id}")
    public Usuario editar(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.editar(id, usuarioDTO);
    }
}
