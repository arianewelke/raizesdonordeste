package com.arianewelke.raizesdonordeste.model.dto;

import com.arianewelke.raizesdonordeste.entity.UsuarioPerfil;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        UsuarioLogin user
) {
    public record UsuarioLogin(Long id, String nome, UsuarioPerfil perfil) {
    }
}
