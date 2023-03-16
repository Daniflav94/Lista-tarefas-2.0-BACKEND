package com.daniele.listatarefas.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.daniele.listatarefas.dto.TarefaDTO;
import com.daniele.listatarefas.exception.RecordNotFoundException;
import com.daniele.listatarefas.model.Tarefa;
import com.daniele.listatarefas.model.Usuario;
import com.daniele.listatarefas.model.enums.Repeticao;
import com.daniele.listatarefas.model.enums.Status;
import com.daniele.listatarefas.repository.TarefaRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    private UsuarioService usuarioService;   

    public TarefaService(TarefaRepository tarefaRepository, UsuarioService usuarioService) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioService = usuarioService;
    }

    public List<Tarefa> listar() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String nome;

        if (principal instanceof UserDetails) {
            nome = ((UserDetails) principal).getUsername();
        } else {
            nome = principal.toString();
        }

        Usuario usuarioLogado = usuarioService.filtrarPorEmail(nome);

        ArrayList<Tarefa> tarefas = new ArrayList<>();

        List<Tarefa> list = this.tarefaRepository.findByUsuario(usuarioLogado.getId());

        list.forEach(tarefa -> {
            if(tarefa.getStatus() == Status.ATIVO){ 
                tarefas.add(tarefa);               
            }   
        });
        System.out.println(tarefas);
        return tarefas;                
    }

    public Tarefa buscarPorId(@PathVariable @NotNull @Positive Long id) { // @PathVariable indica que o parametro será
                                                                          // parte da url
        // @NotNull @Positive por ser tipo Long (objeto), permite valor nulo e números
        // negativos, essas anotações evitam isso
        return tarefaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Tarefa criar(@Valid TarefaDTO tarefaDTO) {
        // @Valid quando for recebida a requisição, vai validar se o JSON está válido de
        // acordo com as validações inseridas
        Tarefa tarefa = new Tarefa();
        tarefa.setNome(tarefaDTO.getNome());
        tarefa.setAnotacao(tarefaDTO.getAnotacao());
        tarefa.setConcluida(tarefaDTO.getConcluida());
        tarefa.setData(tarefaDTO.getData());
        tarefa.setFavorito(tarefaDTO.getFavorito());
        tarefa.setRepeticao(tarefaDTO.getRepeticao());
        tarefa.setCriadaEm(new Date());
        tarefa.setMeuDia(tarefaDTO.getMeuDia());
        tarefa.setLista(tarefaDTO.getLista());
        tarefa.setUsuario(tarefaDTO.getUsuario());

        return tarefaRepository.save(tarefa);
    }

    public Tarefa editar(@NotNull @Positive Long id, @Valid TarefaDTO tarefaDTO) {
        return tarefaRepository.findById(id)
                .map(record -> {
                    record.setNome(tarefaDTO.getNome());
                    record.setAnotacao(tarefaDTO.getAnotacao());
                    record.setConcluida(tarefaDTO.getConcluida());
                    record.setData(tarefaDTO.getData());
                    record.setFavorito(tarefaDTO.getFavorito());
                    record.setRepeticao(tarefaDTO.getRepeticao());
                    record.setMeuDia(tarefaDTO.getMeuDia());
                    record.setDataConclusao(tarefaDTO.getDataConclusao());

                    return tarefaRepository.save(record);
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@PathVariable @NotNull @Positive Long id) {
        tarefaRepository.delete(tarefaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }

    @Scheduled(cron = "0 0 12 * * ?", zone = "America/Sao_Paulo")
    // 0 0 0 * * ?
    public List<Tarefa> verificarPeriodos() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        int dayMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        List<Tarefa> tarefas = tarefaRepository.findAll();

        tarefas.forEach(tarefa -> {
            Calendar calTarefa = Calendar.getInstance();
            calTarefa.setTime(tarefa.getData());
            int dayTarefa = calTarefa.get(Calendar.DAY_OF_WEEK);
            int dayMonthTarefa = calTarefa.get(Calendar.DAY_OF_MONTH);

            Repeticao repeticao = tarefa.getRepeticao();

            if (repeticao == Repeticao.DIARIAMENTE) {
                if (dayMonth > dayMonthTarefa) {
                    Tarefa novaTarefa = new Tarefa();
                    novaTarefa.setData(new Date());
                    novaTarefa.setMeuDia(true);
                    novaTarefa.setAnotacao(tarefa.getAnotacao());
                    novaTarefa.setConcluida(false);
                    novaTarefa.setCriadaEm(tarefa.getCriadaEm());
                    novaTarefa.setFavorito(false);
                    novaTarefa.setNome(tarefa.getNome());
                    novaTarefa.setRepeticao(tarefa.getRepeticao());
                    novaTarefa.setUsuario(tarefa.getUsuario());
                    tarefaRepository.save(novaTarefa);
                }
            } else if (repeticao == Repeticao.SEMANALMENTE) {
                if (day == dayTarefa && dayMonth > dayMonthTarefa) {
                    Tarefa novaTarefa = new Tarefa();
                    novaTarefa.setData(new Date());
                    novaTarefa.setMeuDia(true);
                    novaTarefa.setAnotacao(tarefa.getAnotacao());
                    novaTarefa.setConcluida(false);
                    novaTarefa.setCriadaEm(tarefa.getCriadaEm());
                    novaTarefa.setFavorito(false);
                    novaTarefa.setNome(tarefa.getNome());
                    novaTarefa.setRepeticao(tarefa.getRepeticao());
                    novaTarefa.setUsuario(tarefa.getUsuario());
                    tarefaRepository.save(novaTarefa);
                } else {
                    tarefa.setMeuDia(false);
                    tarefaRepository.save(tarefa);
                }
            } else if (repeticao == Repeticao.MENSALMENTE) {
                switch (month) {
                    case 0, 2, 4, 6, 7, 9, 11:
                        if (dayMonth == dayMonthTarefa) {
                            Tarefa novaTarefa = new Tarefa();
                            novaTarefa.setData(new Date());
                            novaTarefa.setMeuDia(true);
                            novaTarefa.setAnotacao(tarefa.getAnotacao());
                            novaTarefa.setConcluida(false);
                            novaTarefa.setCriadaEm(tarefa.getCriadaEm());
                            novaTarefa.setFavorito(false);
                            novaTarefa.setNome(tarefa.getNome());
                            novaTarefa.setRepeticao(tarefa.getRepeticao());
                            novaTarefa.setUsuario(tarefa.getUsuario());
                            tarefaRepository.save(novaTarefa);
                        } else {
                            tarefa.setMeuDia(false);
                            tarefaRepository.save(tarefa);
                        }
                        break;

                    case 1:
                        if (dayMonthTarefa == 29 || dayMonthTarefa == 30 || dayMonthTarefa == 31) {
                            dayMonthTarefa = 28;
                        }

                        if (dayMonth == dayMonthTarefa) {
                            Tarefa novaTarefa = new Tarefa();
                            novaTarefa.setData(new Date());
                            novaTarefa.setMeuDia(true);
                            novaTarefa.setAnotacao(tarefa.getAnotacao());
                            novaTarefa.setConcluida(false);
                            novaTarefa.setCriadaEm(tarefa.getCriadaEm());
                            novaTarefa.setFavorito(false);
                            novaTarefa.setNome(tarefa.getNome());
                            novaTarefa.setRepeticao(tarefa.getRepeticao());
                            novaTarefa.setUsuario(tarefa.getUsuario());
                            tarefaRepository.save(novaTarefa);
                        } else {
                            tarefa.setMeuDia(false);
                            tarefaRepository.save(tarefa);
                        }
                        break;

                    case 3, 5, 8, 10:
                        if (dayMonthTarefa == 31) {
                            dayMonthTarefa = 30;
                        }

                        if (dayMonth == dayMonthTarefa) {
                            Tarefa novaTarefa = new Tarefa();
                            novaTarefa.setData(new Date());
                            novaTarefa.setMeuDia(true);
                            novaTarefa.setAnotacao(tarefa.getAnotacao());
                            novaTarefa.setConcluida(false);
                            novaTarefa.setCriadaEm(tarefa.getCriadaEm());
                            novaTarefa.setFavorito(false);
                            novaTarefa.setNome(tarefa.getNome());
                            novaTarefa.setRepeticao(tarefa.getRepeticao());
                            novaTarefa.setUsuario(tarefa.getUsuario());
                            tarefaRepository.save(novaTarefa);
                        } else {
                            tarefa.setMeuDia(false);
                            tarefaRepository.save(tarefa);
                        }
                        break;

                }
            }
        });

        return tarefas;
    }

}
