package com.daniele.listatarefas.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.daniele.listatarefas.exception.RecordNotFoundException;

//Essa classe vai dizer a todos  @RestController o que fazer com as excessões
@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RecordNotFoundException.class) //essa anotação recebe como parâmetro a excessão que vai lidar
    @ResponseStatus(HttpStatus.NOT_FOUND) //qual status deseja retornar quando captar a excessão
    public String handleNotFoundException(RecordNotFoundException ex){
        return ex.getMessage();
    }
    
}
