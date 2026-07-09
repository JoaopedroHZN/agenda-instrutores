package br.com.senai.agenda_instrutores.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 2. A nova regra para pedir ao segurança para liberar as nossas rotas!
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita a proteção contra ataques de formulário web (obrigatório para APIs REST / Postman)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Libera TODAS as requisições sem pedir login por enquanto!
                );

        return http.build();
    }
}
