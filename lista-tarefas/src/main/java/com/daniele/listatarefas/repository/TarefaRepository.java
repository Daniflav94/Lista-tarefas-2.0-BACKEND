package com.daniele.listatarefas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daniele.listatarefas.model.Tarefa;

@Repository //indica ao Spring que fará o acesso ao banco de dados
public interface TarefaRepository extends JpaRepository<Tarefa, Long>{ 
    //Com Spring Data conseguimos utilizar interfaces que facilitam o acesso ao banco de dados
    List<Tarefa> findByStatus(String status);

    @Query(value = "SELECT * FROM tarefas WHERE usuario_id = :usuario", nativeQuery = true)
    List<Tarefa> findByStatusAndUsuario(Long usuario);
}
