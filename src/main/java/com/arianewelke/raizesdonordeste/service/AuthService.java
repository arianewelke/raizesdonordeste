package com.arianewelke.raizesdonordeste.service;

import com.arianewelke.raizesdonordeste.model.dto.LoginRequest;
import com.arianewelke.raizesdonordeste.model.dto.LoginResponse;
import com.arianewelke.raizesdonordeste.entity.Usuario;
import com.arianewelke.raizesdonordeste.exception.ApiException;
import com.arianewelke.raizesdonordeste.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email().trim().toLowerCase())
                .orElseThrow(this::credenciaisInvalidas);

        if (!passwordEncoder.matches(request.senha(), usuario.getSenhaHash())) {
            throw credenciaisInvalidas();
        }

        return new LoginResponse(
                tokenService.gerar(usuario),
                "Bearer",
                TokenService.EXPIRACAO_SEGUNDOS,
                new LoginResponse.UsuarioLogin(usuario.getUsuarioId(), usuario.getNome(), usuario.getPerfil())
        );
    }

    private ApiException credenciaisInvalidas() {
        return new ApiException(HttpStatus.UNAUTHORIZED, "CREDENCIAIS_INVALIDAS", "E-mail ou senha inválidos.");
    }
}
