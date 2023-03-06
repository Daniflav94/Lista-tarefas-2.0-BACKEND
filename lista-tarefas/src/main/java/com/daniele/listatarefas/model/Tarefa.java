package com.daniele.listatarefas.model;

import java.util.Date;


import com.daniele.listatarefas.model.enums.Repeticao;
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
@Entity //vai indicar que essa classe será uma entidade no banco de dados
@Table(name = "Tarefas")
public class Tarefa {
    
    @Id // chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // para que esse valor seja gerado automaticamente pelo banco de dados
    @JsonProperty("_id") //isso vai fazer com que quando for transformado em JSON, o _ vá junto
    private Long id;
    
    @Column(length = 100, nullable = false) // define tamanho e que não aceita valores nulos
    private String nome;
    
    private Boolean favorito = false;
      
    private Boolean concluida = false;

    private Date dataConclusao;

    private Date data;

    private Boolean meuDia = false;

    @Column(length = 500)
    private String anotacao;

    @Enumerated(EnumType.STRING)  // representando o enum "escrito"
    private Repeticao repeticao;

    private Date criadaEm;

}
