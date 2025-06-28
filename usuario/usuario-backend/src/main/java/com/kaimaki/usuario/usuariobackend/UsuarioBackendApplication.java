package com.kaimaki.usuario.usuariobackend;

import com.kaimaki.usuario.usuariobackend.config.GeminiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GeminiProperties.class)
public class UsuarioBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuarioBackendApplication.class, args);
    }

}
