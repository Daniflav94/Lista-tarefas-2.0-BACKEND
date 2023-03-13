package com.daniele.listatarefas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CredenciaisDTO {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;
    
}
