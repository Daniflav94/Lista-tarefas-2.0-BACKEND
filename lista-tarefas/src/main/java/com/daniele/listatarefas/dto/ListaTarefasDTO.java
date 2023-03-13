package com.daniele.listatarefas.dto;

import org.hibernate.validator.constraints.Length;

import com.daniele.listatarefas.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListaTarefasDTO {

    @JsonProperty("_id") //isso vai fazer com que quando for transformado em JSON, o _ vá junto
    private Long id;

    @NotBlank //não permite somente 1 caractere espaço
    @NotNull // não permite nulo e vazio
    @Length(min = 2, max = 100)
    private String nome;

    @NotBlank
    @NotNull 
    private Usuario usuario;
    
}
