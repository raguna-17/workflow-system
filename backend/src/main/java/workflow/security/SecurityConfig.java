package workflow.security;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;



@Configuration
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Value("${app.cors.allowed-origin}")
    private String allowedOrigin;



    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }



    @PostConstruct
    public void checkCors() {

        System.out.println(
                "CORS=" + allowedOrigin
        );

    }



    /**
     * パスワード暗号化
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return Argon2PasswordEncoder
                .defaultsForSpringSecurity_v5_8();

    }



    /**
     * AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration

    ) throws Exception {

        return configuration.getAuthenticationManager();

    }



    /**
     * CORS設定
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {


        CorsConfiguration configuration =
                new CorsConfiguration();


        configuration.setAllowedOrigins(
                List.of(allowedOrigin)
        );


        configuration.setAllowedMethods(
                List.of("*")
        );


        configuration.setAllowedHeaders(
                List.of("*")
        );


        configuration.setAllowCredentials(true);



        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();



        source.registerCorsConfiguration(
                "/**",
                configuration
        );


        return source;

    }



    /**
     * Spring Security設定
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http

    ) throws Exception {


        http

                .csrf(csrf ->
                        csrf.disable()
                )


                .cors(cors -> {})


                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                .authorizeHttpRequests(auth -> auth


                        // CORS preflight
                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        )
                        .permitAll()



                        // ユーザー登録
                        .requestMatchers(
                                HttpMethod.POST,
                                "/users",
                                "/auth/login"
                        )
                        .permitAll()



                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()



                        // その他はJWT必須
                        .anyRequest()
                        .authenticated()

                )


                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );



        return http.build();

    }

}