package com.daniele.listatarefas.security;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.daniele.listatarefas.model.Usuario;
import com.daniele.listatarefas.repository.UsuarioRepository;

// carrega a entidade Usuario do banco de dados e converte para o UsuarioSecurity
@Service
public class UsuarioSecurityService  implements UserDetailsService{

    private UsuarioRepository usuarioRepository;

    public UsuarioSecurityService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // se comunica com o banco de dados em busca do usuario que tem username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = this.usuarioRepository.findByEmail(username);
        //vai buscar no banco o usuário com o email indicado em username
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException(username));

        return new UsuarioSecurity(usuario.getEmail(), usuario.getSenha(), usuario.getPerfil());
    }

    
    
}
