package com.daniele.listatarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniele.listatarefas.model.Tarefa;

@Repository //indica ao Spring que fará o acesso ao banco de dados
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{ 
    //Com Spring Data conseguimos utilizar interfaces que facilitam o acesso ao banco de dados
    
}
