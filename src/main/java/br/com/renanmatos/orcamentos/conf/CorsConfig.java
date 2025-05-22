package br.com.renanmatos.orcamentos.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Define uma classe de configuração para habilitar CORS no Spring Boot
@Configuration
public class CorsConfig {

    // Cria um bean para configurar CORS no aplicativo
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // Permite requisições de qualquer origem
                registry.addMapping("/**")
                        .allowedOrigins("*") // Aceita chamadas de qualquer domínio
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Per-mite esses métodos HTTP
                        .allowedHeaders("*"); // Permite qualquer cabeçalho na requisição
            }
        };
    }
}
