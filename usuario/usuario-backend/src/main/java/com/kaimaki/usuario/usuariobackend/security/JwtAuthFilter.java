package com.kaimaki.usuario.usuariobackend.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Logging para debug
        System.out.println("=== JWT FILTER DEBUG ===");
        System.out.println("Authorization Header: " + authHeader);
        System.out.println("Request URI: " + request.getRequestURI());

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("No hay token Bearer válido");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        System.out.println("Token extraído: " + token.substring(0, Math.min(20, token.length())) + "...");

        if (!jwtService.validateToken(token)) {
            System.out.println("Token inválido");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String correo = jwtService.getCorreoFromToken(token);
            String rol = jwtService.getRolFromToken(token); // ✅ YA TIENES ESTE MÉTODO

            System.out.println("Correo extraído: " + correo);
            System.out.println("Rol extraído del JWT: " + rol);

            if (correo != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Crear UserDetails simple con el rol del JWT
                Collection<SimpleGrantedAuthority> authorities =
                        Arrays.asList(new SimpleGrantedAuthority(rol));

                UserDetails userDetails = User.builder()
                        .username(correo)
                        .password("") // No necesitamos la password para autenticación JWT
                        .authorities(authorities)
                        .build();

                System.out.println("Authorities asignadas: " + authorities);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

                System.out.println("Autenticación establecida correctamente");
            }
        } catch (Exception e) {
            System.out.println("Error procesando JWT: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }
}