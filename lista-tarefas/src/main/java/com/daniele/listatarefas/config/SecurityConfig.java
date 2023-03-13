package com.daniele.listatarefas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.daniele.listatarefas.security.TokenFilter;

import java.util.List;

@Configuration
@EnableWebSecurity // Indica que esta classe irá configurar a segurança da aplicação
@EnableMethodSecurity(prePostEnabled = true) // Anotação que tem a ver com autorização de acesso
public class SecurityConfig {
    // /auth/login, /auth/cadastro, /auth/logout
    // /cargos/** -> /cargos/filtrar, /cargos/1
    private static final String[] PUBLIC_ENDPOINTS = {"/auth/**"}; // aqui iremos colocar as principais rotas publicas

    @Autowired
    private TokenFilter filter;

    @Bean // esta anotação permite que retornemos um objeto que pode ser injetado em outras classes
    public PasswordEncoder passwordEncoder() {
        // definimos aqui qual será o algoritmo para encriptar as senhas
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager é classe por trás de toda o gerenciamento da autenticação do usuário
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // Tornamos a instancia do gerenciador de autenticação disponivel para injeção em outras classes
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Definimos o CORS como habilitado e desabilitamos o CSRF pois não tem necessidade ativar
        http.cors().and().csrf().disable();
        // Aqui podemos permitir/autorizar ou bloquear certos endpoints da aplicação
        http.authorizeHttpRequests()
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll() // possibilita os endpoints no array serem PUBLICOS
                .anyRequest().authenticated();
        // Aqui indicamos que não há sessão de usuário, apenas o JWT garante que o usuário possui autorização para acessar
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Indicamos nosso filtro antes do filtro que valida a senha e email
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        // Criamos uma nova configuração do CORS e habilitamos os 4 verbos mais usados. Em seguida indicamos quais URL serão permitidas pelo CORS
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.addAllowedOrigin("http://localhost:4200");
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }
}
