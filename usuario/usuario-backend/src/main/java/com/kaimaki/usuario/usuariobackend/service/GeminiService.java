package com.kaimaki.usuario.usuariobackend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaimaki.usuario.usuariobackend.config.GeminiProperties;
import com.kaimaki.usuario.usuariobackend.repository.OficioRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class GeminiService {

    private final GeminiProperties geminiProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final OficioRepository oficioRepository;
    public GeminiService(GeminiProperties geminiProperties, OficioRepository oficioRepository) {
        this.geminiProperties = geminiProperties;
        this.oficioRepository = oficioRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }
    public String obtenerOficioDesdeGemini(String mensaje) {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + geminiProperties.getApiKey();

        Map<String, Object> part = Map.of("text", "¿Qué oficio realiza este trabajo?: " + mensaje);
        Map<String, Object> content = Map.of("parts", List.of(part));
        Map<String, Object> body = Map.of("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode textNode = root.at("/candidates/0/content/parts/0/text");
                String textoGemini = textNode.asText().toLowerCase();

                // Extraer el posible oficio usando regex
                Pattern pattern = Pattern.compile("(electricista|plomero|gasfitero|albañil|carpintero|pintor|técnico en refrigeración|técnico en lavadoras)");
                Matcher matcher = pattern.matcher(textoGemini);

                if (matcher.find()) {
                    String oficioDetectado = matcher.group(1).toLowerCase();
                    List<String> oficiosValidos = oficioRepository.obtenerNombresDeOficios();

                    if (oficiosValidos.contains(oficioDetectado)) {
                        return oficioDetectado;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(" Error al llamar a Gemini: " + e.getMessage());
        }

        return "trabajador"; // Por defecto si no se encuentra un oficio válido
    }


}