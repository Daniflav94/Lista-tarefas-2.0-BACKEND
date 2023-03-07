package com.daniele.listatarefas.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.daniele.listatarefas.dto.ListaTarefasDTO;
import com.daniele.listatarefas.exception.RecordNotFoundException;
import com.daniele.listatarefas.model.ListaTarefas;
import com.daniele.listatarefas.repository.ListaTarefasRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class ListaTarefasService {

    private final ListaTarefasRepository listaTarefasRepository;

    public ListaTarefasService(ListaTarefasRepository listaTarefasRepository) {
        this.listaTarefasRepository = listaTarefasRepository;
    }

    public List<ListaTarefas> listar(){
        return listaTarefasRepository.findAll();
    }

    @Validated
    public ListaTarefas listarPorId(@PathVariable @NotNull @Positive Long id) {
        return listaTarefasRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
    
    public ListaTarefas criar(@Valid ListaTarefasDTO listaDTO) {
        ListaTarefas listaTarefas  = new ListaTarefas();
        listaTarefas.setNome(listaDTO.getNome());
        
        return listaTarefasRepository.save(listaTarefas);
    }

    @Validated
    public ListaTarefas editar(@NotNull @Positive Long id, @Valid ListaTarefasDTO listaDTO) {
        return listaTarefasRepository.findById(id)
        .map(record -> {
            record.setNome(listaDTO.getNome());

            return listaTarefasRepository.save(record);
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Validated
    public void deletar(@PathVariable @NotNull @Positive Long id) {
        listaTarefasRepository.delete(
            listaTarefasRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
