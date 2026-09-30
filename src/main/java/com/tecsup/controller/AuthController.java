package com.tecsup.controller;

import com.tecsup.model.Usuario;
import com.tecsup.repository.UsuarioRepository;
import com.tecsup.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public record LoginRequest(@NotBlank String username, @NotBlank String password) {}

    public record RegisterRequest(@NotBlank @Size(min = 3, max = 50) String username,
                                  @NotBlank @Size(min = 6) String password,
                                  @NotBlank String nombre) {}

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        return usuarioRepository.findByUsername(req.username())
                .filter(u -> passwordEncoder.matches(req.password(), u.getPassword()))
                .<ResponseEntity<?>>map(u -> ResponseEntity.ok(Map.of(
                        "token", jwtService.generarToken(u.getUsername()),
                        "username", u.getUsername(),
                        "nombre", u.getNombre()
                )))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Usuario o contrasena incorrectos")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (usuarioRepository.existsByUsername(req.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "El usuario ya existe"));
        }
        Usuario u = new Usuario(req.username(), passwordEncoder.encode(req.password()), req.nombre());
        usuarioRepository.save(u);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("mensaje", "Usuario registrado correctamente"));
    }
}
