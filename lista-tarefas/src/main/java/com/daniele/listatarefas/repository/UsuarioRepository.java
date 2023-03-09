package com.daniele.listatarefas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daniele.listatarefas.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    Optional<Usuario> findByEmail(String email);
    
}
