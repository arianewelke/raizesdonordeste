package com.arianewelke.raizesdonordeste.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="usuario")
@Getter
@Setter
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    private UsuarioPerfil perfil;

    private boolean consentimentoLgpd;
    private LocalDateTime dataConsentimento;

    public Usuario() {
    }

    public Usuario(Long usuarioId, String nome, String email, String senhaHash, UsuarioPerfil perfil, boolean consentimentoLgpd, LocalDateTime dataConsentimento) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.perfil = perfil;
        this.consentimentoLgpd = consentimentoLgpd;
        this.dataConsentimento = dataConsentimento;
    }
}
