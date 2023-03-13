package com.daniele.listatarefas.model;

import com.daniele.listatarefas.model.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    protected Long id;

    @Column(length = 100, nullable = false)
    protected String nome;

    @Column(length = 150, nullable = false, unique = true)
    protected String email;

    @JsonIgnore // impede a leitura da senha no JSON
    @Column(nullable = false)
    protected String senha;

    protected String foto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Perfil perfil; // Indica o que este usuário é no sistema

    
}
