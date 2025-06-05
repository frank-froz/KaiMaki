package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import com.kaimaki.usuario.usuariobackend.security.JwtService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Value("${google.client.id}")
    private String clientId;

    private GoogleIdTokenVerifier verifier;

    // Inicializamos el verifier en un método PostConstruct
    @jakarta.annotation.PostConstruct
    public void init() throws Exception {
        verifier = new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance()
        )
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body) {
        String idTokenString = body.get("idToken");
        System.out.println("Token de Google recibido: " + idTokenString);// Para depuración
        if (idTokenString == null) {
            return ResponseEntity.badRequest().body("idToken es requerido");
        }

        try {
            GoogleIdToken.Payload payload = verifyGoogleToken(idTokenString);
            if (payload == null) {
                return ResponseEntity.status(401).body("Token inválido o expirado");
            }

            String email = payload.getEmail();


            if (!email.endsWith("@tecsup.edu.pe")) {
                return ResponseEntity.status(403).body("Solo se permiten correos @tecsup.edu.pe");
            }

            // Buscar o crear usuario
            User usuario = userRepository.findByCorreo(email)
                    .orElseGet(() -> {
                        User nuevo = new User();
                        nuevo.setCorreo(email);
                        nuevo.setNombre(payload.get("name").toString());
                        // Ajusta otros campos si los tienes
                        return userRepository.save(nuevo);
                    });

            // Generar JWT para tu app
            String token = jwtService.generateToken(email);

            return ResponseEntity.ok(Map.of(
                    "usuario", usuario,
                    "token", token
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }


    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) throws Exception {
        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            return idToken.getPayload();
        } else {
            return null;
        }
    }
}
