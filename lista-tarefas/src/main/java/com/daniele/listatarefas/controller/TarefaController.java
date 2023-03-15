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

import com.daniele.listatarefas.dto.TarefaDTO;
import com.daniele.listatarefas.model.Tarefa;
import com.daniele.listatarefas.service.TarefaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated // precisa dessa anotação para ativar as validações @NotNull e @Positive
@RestController // indica ao spring que essa classe contem um endpoint
@RequestMapping("/api/tarefas") // endpoint que vai ficar exposto
public class TarefaController {


    private final TarefaService tarefaService;

    
    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping 
    public List<Tarefa> listar() {
        return tarefaService.listar();               
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @GetMapping("/{id}") //precisamos passar o parametro dinâmico que será inserido no final da url
    public Tarefa buscarPorId(@PathVariable @NotNull @Positive Long id) { //@PathVariable indica que o parametro será parte da url
        //@NotNull @Positive por ser tipo Long (objeto), permite valor nulo e números negativos, essas anotações evitam isso
        return tarefaService.buscarPorId(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Tarefa criar(@RequestBody @Valid TarefaDTO tarefa) { 
        //@Valid quando for recebida a requisição, vai validar se o JSON está válido de acordo com as validações inseridas
        //RequestBody mapeia o corpo da solicitação e compara com o tipo informado
        //Retorno ResponseEntity vai retornar o status 201 (created) invés do 200, 
        //mas pode ser usado como anotação @ResponseStatus em caso de apenas retornar o status
        
        /* return ResponseEntity.status(HttpStatus.CREATED)
        .body(tarefaRepository.save(tarefa)); */
        return tarefaService.criar(tarefa);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @PutMapping("/{id}")
    public Tarefa editar(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid TarefaDTO tarefa) {
        return tarefaService.editar(id, tarefa);       
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USUARIO')")
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
       tarefaService.delete(id);
    }

}
