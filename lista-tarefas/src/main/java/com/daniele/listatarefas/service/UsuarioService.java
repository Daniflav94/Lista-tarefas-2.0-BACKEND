package com.daniele.listatarefas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.daniele.listatarefas.dto.UsuarioDTO;
import com.daniele.listatarefas.exception.RecordNotFoundException;
import com.daniele.listatarefas.model.Usuario;
import com.daniele.listatarefas.model.enums.Perfil;
import com.daniele.listatarefas.repository.UsuarioRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario filtrarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new RuntimeException());
    }

    public Usuario buscarPorId(@PathVariable @NotNull @Positive Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Usuario salvar(@Valid UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();

        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setPerfil(Perfil.USUARIO);
        usuario.setSenha(encoder.encode(usuarioDTO.getSenha()));

        return usuarioRepository.save(usuario);   
    }

    public Usuario editar(@PathVariable @NotNull @Positive Long id, @Valid UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id)
        .map(record -> {
            record.setFoto(usuarioDTO.getFoto());
            record.setTemaMeuDia(usuarioDTO.getTemaMeuDia());
            record.setTemaHome(usuarioDTO.getTemaHome());
            record.setTemaImportante(usuarioDTO.getTemaImportante());

            return usuarioRepository.save(record);
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }
    
}
