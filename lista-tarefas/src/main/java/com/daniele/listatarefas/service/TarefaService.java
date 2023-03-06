package com.daniele.listatarefas.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;

import com.daniele.listatarefas.dto.TarefaDTO;
import com.daniele.listatarefas.exception.RecordNotFoundException;
import com.daniele.listatarefas.model.Tarefa;
import com.daniele.listatarefas.model.enums.Repeticao;
import com.daniele.listatarefas.repository.TarefaRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public List<Tarefa> listar() {
        return tarefaRepository.findAll();
    }

    @Validated
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
        return tarefaRepository.save(tarefa);
    }

    @Validated
    public Tarefa editar(@NotNull @Positive Long id, @Valid Tarefa tarefa) {
        Date dataAtual = new Date();

        return tarefaRepository.findById(id)
                .map(record -> {
                    record.setNome(tarefa.getNome());
                    record.setAnotacao(tarefa.getAnotacao());
                    record.setConcluida(tarefa.getConcluida());
                    record.setData(tarefa.getData());
                    record.setFavorito(tarefa.getFavorito());
                    record.setRepeticao(tarefa.getRepeticao());

                    if (record.getData() == dataAtual) {
                        record.setMeuDia(true);
                    } else {
                        record.setMeuDia(false);
                    }

                    return tarefaRepository.save(tarefa);
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@PathVariable @NotNull @Positive Long id) {
        tarefaRepository.delete(tarefaRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }


    @Scheduled(cron = "0 0 0 * * ?", zone = "America/Sao_Paulo")
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
                    tarefaRepository.save(novaTarefa);
                }
            } else if (repeticao == Repeticao.SEMANALMENTE) {
                if (day == dayTarefa) {
                    Tarefa novaTarefa = new Tarefa();
                    novaTarefa.setData(new Date());
                    novaTarefa.setMeuDia(true);
                    novaTarefa.setAnotacao(tarefa.getAnotacao());
                    novaTarefa.setConcluida(false);
                    novaTarefa.setCriadaEm(tarefa.getCriadaEm());
                    novaTarefa.setFavorito(false);
                    novaTarefa.setNome(tarefa.getNome());
                    novaTarefa.setRepeticao(tarefa.getRepeticao());
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