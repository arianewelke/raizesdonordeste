package com.arianewelke.raizesdonordeste.service;

import com.arianewelke.raizesdonordeste.model.dto.UsuarioCadastroRequest;
import com.arianewelke.raizesdonordeste.model.dto.UsuarioResponse;
import com.arianewelke.raizesdonordeste.entity.Usuario;
import com.arianewelke.raizesdonordeste.entity.UsuarioPerfil;
import com.arianewelke.raizesdonordeste.exception.ApiException;
import com.arianewelke.raizesdonordeste.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponse cadastrar(UsuarioCadastroRequest request) {
        String email = request.email().trim().toLowerCase();

        if (usuarioRepository.existsByEmail(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "EMAIL_JA_CADASTRADO", "E-mail já cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.nome().trim());
        usuario.setEmail(email);
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        usuario.setPerfil(UsuarioPerfil.CLIENTE); // cadastro público nunca cria funcionário
        usuario.setConsentimentoLgpd(request.consentimentoLgpd());
        if (request.consentimentoLgpd()) {
            usuario.setDataConsentimento(LocalDateTime.now());
        }

        return UsuarioResponse.de(usuarioRepository.save(usuario));
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "RECURSO_NAO_ENCONTRADO", "Usuário não encontrado."));
        return UsuarioResponse.de(usuario);
    }

}
