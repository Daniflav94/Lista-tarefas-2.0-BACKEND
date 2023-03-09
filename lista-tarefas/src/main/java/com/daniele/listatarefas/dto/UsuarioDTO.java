package com.daniele.listatarefas.dto;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDTO {

    @JsonProperty("_id")
    protected Long id;

    @NotBlank
    @NotNull 
    @Length(min = 2, max = 100)
    protected String nome;

    @NotBlank
    @Email
    protected String email;

    @NotBlank
    @NotNull 
    protected String senha;
    
}
