package com.daniele.listatarefas.model;

import java.util.Date;

import org.hibernate.annotations.SQLDelete;

import com.daniele.listatarefas.model.enums.Repeticao;
import com.daniele.listatarefas.model.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity //vai indicar que essa classe será uma entidade no banco de dados
@Table(name = "Tarefas")
@SQLDelete(sql = "UPDATE Tarefas SET status = 'INATIVO' WHERE id = ?") //toda vez que o método delete for acionado, vai executar esse código do SQL
//@Where(clause = "status = 'Ativo'")//toda vez que for feito SELECT, o hibernate vai automaticamente adicionar a cláusula WHERE e aplicar o filtro
public class Tarefa {
    
    @Id // chave primária
    @GeneratedValue(strategy = GenerationType.AUTO) // para que esse valor seja gerado automaticamente pelo banco de dados
    @JsonProperty("_id") //isso vai fazer com que quando for transformado em JSON, o _ vá junto
    protected Long id;
    
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

    @ManyToOne
    //@JoinColumn(name = "idLista") //cria uma coluna nova que é a chave estrangeira e ListaTarefas
    private ListaTarefas lista;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    @ManyToOne
    private Usuario usuario;

}
