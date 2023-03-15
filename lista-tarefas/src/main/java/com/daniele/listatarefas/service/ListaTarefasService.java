package com.daniele.listatarefas.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.daniele.listatarefas.dto.ListaTarefasDTO;
import com.daniele.listatarefas.exception.RecordNotFoundException;
import com.daniele.listatarefas.model.ListaTarefas;
import com.daniele.listatarefas.model.Usuario;
import com.daniele.listatarefas.repository.ListaTarefasRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class ListaTarefasService {

    private final ListaTarefasRepository listaTarefasRepository;

    private UsuarioService usuarioService;

    

    public ListaTarefasService(ListaTarefasRepository listaTarefasRepository, UsuarioService usuarioService) {
        this.listaTarefasRepository = listaTarefasRepository;
        this.usuarioService = usuarioService;
    }

    public List<ListaTarefas> listar(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Usuario usuarioLogado = usuarioService.filtrarPorEmail(nome);

        return listaTarefasRepository.findByStatusAndUsuario(usuarioLogado.getId());
    }

    @Validated
    public ListaTarefas listarPorId(@PathVariable @NotNull @Positive Long id) {
        return listaTarefasRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }
    
    public ListaTarefas criar(@Valid ListaTarefasDTO listaDTO) {
        ListaTarefas listaTarefas  = new ListaTarefas();
        listaTarefas.setNome(listaDTO.getNome());
        listaTarefas.setUsuario(listaDTO.getUsuario());
        
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
