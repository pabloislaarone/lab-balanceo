package com.tecsup.config;

import com.tecsup.model.Usuario;
import com.tecsup.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/** Crea el usuario admin / admin123 si todavia no existe. */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        try {
            if (!usuarioRepository.existsByUsername("admin")) {
                usuarioRepository.save(new Usuario("admin", passwordEncoder.encode("admin123"), "Administrador"));
                log.info("Usuario por defecto creado: admin / admin123");
            }
        } catch (Exception e) {
            // Si otra instancia lo creo al mismo tiempo, no pasa nada
            log.info("El usuario admin ya existia (creado por otra instancia)");
        }
    }
}
