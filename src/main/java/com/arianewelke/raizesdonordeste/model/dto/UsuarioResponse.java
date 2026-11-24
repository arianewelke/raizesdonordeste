package com.arianewelke.raizesdonordeste.model.dto;

import com.arianewelke.raizesdonordeste.entity.Usuario;
import com.arianewelke.raizesdonordeste.entity.UsuarioPerfil;

public record UsuarioResponse(
        Long id,
        String nome,
        String email,
        UsuarioPerfil perfil,
        boolean consentimentoLgpd
) {
    public static UsuarioResponse de(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getUsuarioId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil(),
                usuario.isConsentimentoLgpd()
        );
    }
}
