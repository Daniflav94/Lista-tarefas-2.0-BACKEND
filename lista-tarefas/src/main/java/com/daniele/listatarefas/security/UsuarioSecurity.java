package com.daniele.listatarefas.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.daniele.listatarefas.model.enums.Perfil;


//esa classe serve de ponte entre a persistência (banco de dados) e o Spring Security
public class UsuarioSecurity implements UserDetails{

    private String email;

    private String senha;

    private Perfil perfil;

    private ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

    public UsuarioSecurity(String email, String senha, Perfil perfil) {
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
        this.authorities.add(new SimpleGrantedAuthority(perfil.getDescricao()));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //conta válida
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // indica se a conta está desbloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //credenciais estão válidas?
    }

    @Override
    public boolean isEnabled() {
        return true; //indica se o usuário está habilitado
    }
    
    
    
}
