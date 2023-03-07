package com.daniele.listatarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daniele.listatarefas.model.ListaTarefas;

@Repository
public interface ListaTarefasRepository extends JpaRepository<ListaTarefas, Long> {
    
}
