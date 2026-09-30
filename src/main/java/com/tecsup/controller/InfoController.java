package com.tecsup.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/** Endpoint publico para demostrar que instancia atiende cada peticion. */
@RestController
public class InfoController {

    @GetMapping("/api/info")
    public Map<String, Object> info(HttpServletRequest request) {
        Map<String, Object> datos = new LinkedHashMap<>();
        try {
            InetAddress local = InetAddress.getLocalHost();
            datos.put("hostname", local.getHostName());
            datos.put("ip", local.getHostAddress());
        } catch (Exception e) {
            datos.put("hostname", "desconocido");
        }
        datos.put("puerto", request.getLocalPort());
        datos.put("hora", LocalDateTime.now().toString());
        return datos;
    }
}
