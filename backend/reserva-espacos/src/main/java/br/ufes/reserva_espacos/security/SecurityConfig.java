package br.ufes.reserva_espacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    

    private final JwtService jwtService;

    public SecurityConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtService);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        
        http
            //Desabilitand configuração que mantém usuario logado se abrir outra Aba, algo assim, tmj
            .csrf(csrf -> csrf.disable())

            // Não utiliza sessões
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Define as permissões das rotas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .requestMatchers("/pages/**").permitAll()
                .requestMatchers("/assets/css/**").permitAll()
                .requestMatchers("/assets/img/**").permitAll()
                .requestMatchers("/assets/js/**").permitAll()

                .requestMatchers("/usuarios/admin").hasRole("ADMINISTRADOR")

                .anyRequest().authenticated()
            )

            // Desabilita o formulário de login padrão
            .formLogin(form -> form.disable())

            // Desabilita autenticação HTTP Basic
            .httpBasic(httpBasic -> httpBasic.disable())

            .addFilterBefore(
                jwtAuthenticationFilter(),
                UsernamePasswordAuthenticationFilter.class
            );
            

        return http.build();
    }
}
