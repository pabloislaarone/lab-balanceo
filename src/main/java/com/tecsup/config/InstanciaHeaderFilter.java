package com.tecsup.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Agrega a TODAS las respuestas la cabecera "X-Servido-Por" con el hostname y puerto
 * de la instancia que atendio la peticion. Sirve como evidencia del balanceo.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class InstanciaHeaderFilter extends OncePerRequestFilter {

    private final String hostname;

    public InstanciaHeaderFilter() {
        String h;
        try {
            h = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            h = "desconocido";
        }
        this.hostname = h;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        response.setHeader("X-Servido-Por", hostname + ":" + request.getLocalPort());
        chain.doFilter(request, response);
    }
}
