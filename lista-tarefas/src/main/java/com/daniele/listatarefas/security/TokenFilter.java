package com.daniele.listatarefas.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//TokenFilter irá verificar o token do cliente uma vez por requisição

@Component
public class TokenFilter extends OncePerRequestFilter {

    private TokenUtil tokenUtil;

    private UsuarioSecurityService usuarioSecurityService;

    public TokenFilter(TokenUtil tokenUtil, UsuarioSecurityService usuarioSecurityService) {
        this.tokenUtil = tokenUtil;
        this.usuarioSecurityService = usuarioSecurityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (!validarCabecalho(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = this.extrairToken(request);

        if (!this.tokenUtil.validarToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String email = this.tokenUtil.extrairEmail(token);
        UserDetails usuario = this.usuarioSecurityService.loadUserByUsername(email);

        //Configura o usuário encontrado como autenticado na aplicação
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword(), usuario.getAuthorities()));
        filterChain.doFilter(request, response);

        //Resumo: Extrair do cabeçalho as informações do token, com base nessas informações
        //busca dados do usuário e então indica para a segurança da aplicação que o usuário é válido
        //os próximos filtros já irão conhecer o usuário e permitir o acesso
    }

    private String extrairToken(HttpServletRequest request) {
        String cabecalho = request.getHeader("Authorization");

        return cabecalho.substring(7); // vai cortar o Bearer e pegar o código do JWT
    }

    private boolean validarCabecalho(HttpServletRequest request) {
        String cabecalho = request.getHeader("Authorization");

        return cabecalho != null && cabecalho.startsWith("Bearer");
    }

}
