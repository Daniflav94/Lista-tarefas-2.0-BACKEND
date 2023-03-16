package com.daniele.listatarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.daniele.listatarefas.model.ListaTarefas;

import java.util.List;

@Repository
public interface ListaTarefasRepository extends JpaRepository<ListaTarefas, Long> {
    @Query(value = "SELECT * FROM listas WHERE usuario_id = :usuario", nativeQuery = true)
    List<ListaTarefas> findByUsuario(Long usuario);
}
