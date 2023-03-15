package com.daniele.listatarefas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.daniele.listatarefas.dto.ListaTarefasDTO;
import com.daniele.listatarefas.model.ListaTarefas;
import com.daniele.listatarefas.service.ListaTarefasService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/lista")
public class ListaTarefasController {

    private final ListaTarefasService listaTarefasService;

    public ListaTarefasController(ListaTarefasService listaTarefasService) {
        this.listaTarefasService = listaTarefasService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping
    public List<ListaTarefas> listar() {
        return listaTarefasService.listar();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping("/{id}")
    public ListaTarefas listarPorId(@PathVariable @NotNull @Positive Long id) {
        return listaTarefasService.listarPorId(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ListaTarefas criar(@RequestBody @Valid ListaTarefasDTO listaDTO) {
        return listaTarefasService.criar(listaDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @PutMapping("/{id}")
    public ListaTarefas editar(@PathVariable @NotNull @Positive Long id, @Valid @RequestBody ListaTarefasDTO listaDTO) {
        return listaTarefasService.editar(id, listaDTO);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable @NotNull @Positive Long id) {
        listaTarefasService.deletar(id);
    }
    
}
