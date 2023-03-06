package com.daniele.listatarefas.dto;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.daniele.listatarefas.model.enums.Repeticao;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TarefaDTO {

    @JsonProperty("_id") //isso vai fazer com que quando for transformado em JSON, o _ vá junto
    private Long id;
  
    @NotBlank //não permite somente 1 caractere espaço
    @NotNull // não permite nulo e vazio
    @Length(min = 2, max = 100)
    private String nome;
    
    private Boolean favorito = false;
      
    private Boolean concluida = false;

    private Date dataConclusao;

    private Date data;

    private Boolean meuDia = false;

    private String anotacao;

    @Enumerated(EnumType.STRING)  // representando o enum "escrito"
    private Repeticao repeticao;

}
