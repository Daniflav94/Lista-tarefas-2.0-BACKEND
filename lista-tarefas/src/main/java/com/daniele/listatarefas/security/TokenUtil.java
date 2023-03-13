package com.daniele.listatarefas.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;


// essa classe gera tokens, valida e extrai dados do jwt
@Component // instancia automaticamente o TokenUtil
public class TokenUtil {

    @Value("${senhaJwt}") //pega o valor da variável inserida em application.properties
    private String senhaJwt;

    @Value("${validadeJwt}")
    private Long validadeJwt;

    public String gerarToken(String email, String perfil) {
        return JWT.create()
        .withSubject(email)
        .withClaim("perfil", perfil)
        .withExpiresAt(new Date(System.currentTimeMillis() + this.validadeJwt)) //pega o tempo atual e soma a validade
        .sign(Algorithm.HMAC512(this.senhaJwt));
    }

    public String extrairEmail(String token) {
        return JWT.require(Algorithm.HMAC512(this.senhaJwt))
        .build()
        .verify(token)
        .getSubject();
    }

    public boolean validarToken(String token) {
        try{
            JWT.require(Algorithm.HMAC512(this.senhaJwt))
            .build()
            .verify(token);

            return true;

        } catch(JWTVerificationException ex){
            return false;
        }
    }
    
}
