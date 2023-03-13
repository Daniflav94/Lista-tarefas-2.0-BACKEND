package com.daniele.listatarefas.model.enums;

public enum Perfil {
    ADMIN("ROLE_ADMIN"), 
    USUARIO("ROLE_USUARIO"); 

    private String descricao;

    Perfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
