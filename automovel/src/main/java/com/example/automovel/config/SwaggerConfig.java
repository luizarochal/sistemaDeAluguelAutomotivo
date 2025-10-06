package com.example.automovel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sistema de Aluguel Automotivo API")
                        .description("Sistema WEB para gerenciamento de aluguel de automóveis")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Suporte")
                                .email("suporte@aluguelautomotivo.com")));
    }
}