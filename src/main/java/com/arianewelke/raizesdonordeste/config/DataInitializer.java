package com.arianewelke.raizesdonordeste.config;

import com.arianewelke.raizesdonordeste.entity.Usuario;
import com.arianewelke.raizesdonordeste.entity.UsuarioPerfil;
import com.arianewelke.raizesdonordeste.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.seed.enabled:false}")
    private boolean seedHabilitado;

    @Value("${app.seed.password:Senha@123}")
    private String senhaPadrao;

    @Override
    public void run(String... args) {
        if (!seedHabilitado) {
            return;
        }
        criarSeNaoExistir("Cliente Teste", "cliente@raizes.com", UsuarioPerfil.CLIENTE, true);
        criarSeNaoExistir("Atendente Teste", "atendente@raizes.com", UsuarioPerfil.ATENDENTE, false);
        criarSeNaoExistir("Cozinha Teste", "cozinha@raizes.com", UsuarioPerfil.COZINHA, false);
        criarSeNaoExistir("Gerente Teste", "gerente@raizes.com", UsuarioPerfil.GERENTE, false);
    }

    private void criarSeNaoExistir(String nome, String email, UsuarioPerfil perfil, boolean consentimento) {
        if (usuarioRepository.existsByEmail(email)) {
            return;
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenhaHash(passwordEncoder.encode(senhaPadrao));
        usuario.setPerfil(perfil);
        usuario.setConsentimentoLgpd(consentimento);
        if (consentimento) {
            usuario.setDataConsentimento(LocalDateTime.now());
        }
        usuarioRepository.save(usuario);
    }
}