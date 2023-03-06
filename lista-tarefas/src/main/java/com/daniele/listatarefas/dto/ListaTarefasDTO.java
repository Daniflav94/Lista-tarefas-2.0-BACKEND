package com.daniele.listatarefas.dto;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ListaTarefasDTO {

    @NotBlank //não permite somente 1 caractere espaço
    @NotNull // não permite nulo e vazio
    @Length(min = 2, max = 100)
    private String nome;
    
}
