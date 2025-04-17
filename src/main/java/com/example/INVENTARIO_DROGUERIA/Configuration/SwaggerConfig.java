package com.example.INVENTARIO_DROGUERIA.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI().info(new Info()
                .title("API Papeleria")
                .version("1.0")
                .description("Proyecto para gestionar los procesos de una papeleria")
                .contact(new Contact()
                        .name("Soporte API")
                        .email("juego4089@gmail.com")));
    }
}