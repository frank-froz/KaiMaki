package com.kaimaki.usuario.usuariobackend.controller;

import com.kaimaki.usuario.usuariobackend.dto.UserResponseDTO;
import com.kaimaki.usuario.usuariobackend.model.Rol;
import com.kaimaki.usuario.usuariobackend.model.User;
import com.kaimaki.usuario.usuariobackend.repository.RolRepository;
import com.kaimaki.usuario.usuariobackend.repository.UserRepository;
import com.kaimaki.usuario.usuariobackend.security.GoogleClientProperties;
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
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private GoogleClientProperties googleClientProperties;

    private GoogleIdTokenVerifier verifier;

    // Inicializamos el verifier en un método PostConstruct
    @jakarta.annotation.PostConstruct
    public void init() throws Exception {
        verifier = new GoogleIdTokenVerifier.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance()
        )
                .setAudience(googleClientProperties.getIds())
                .build();
    }
    @PostMapping("/google")
    public ResponseEntity<?> loginWithGoogle(@RequestBody Map<String, String> body) {
        String idTokenString = body.get("idToken");
        System.out.println("Token de Google recibido: " + idTokenString);

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

            // Buscar usuario o crearlo si no existe
            User usuario = userRepository.findByCorreo(email)
                    .orElseGet(() -> {
                        User nuevo = new User();
                        nuevo.setCorreo(email);
                        nuevo.setNombre((String) payload.get("given_name"));   // nombre
                        nuevo.setApellido((String) payload.get("family_name")); // apellido
                        nuevo.setTelefono("000000000");
                        nuevo.setEstadoId(1); // Estado activo por defecto

                        // Foto de perfil desde el token de Google
                        nuevo.setFotoPerfil((String) payload.get("picture"));

                        Rol rolCliente = rolRepository.findById(1L)
                                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));
                        nuevo.setRol(rolCliente);

                        return userRepository.save(nuevo);
                    });

            // Asegurarse de que el rol esté cargado
            if (usuario.getRol() == null) {
                usuario = userRepository.findById(usuario.getId()).orElseThrow();
            }

            String rolNombre = "ROLE_" + usuario.getRol().getNombre().toUpperCase();
            String token = jwtService.generateToken(usuario.getCorreo(), rolNombre);

            // ⚠USAR DTO para evitar problemas de serialización
            UserResponseDTO userDTO = new UserResponseDTO(usuario);

            return ResponseEntity.ok(Map.of(
                    "usuario", userDTO,
                    "token", token
            ));

        } catch (Exception e) {
            e.printStackTrace();
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