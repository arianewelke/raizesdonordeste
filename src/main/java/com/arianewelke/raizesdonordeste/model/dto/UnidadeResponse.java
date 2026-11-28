package com.arianewelke.raizesdonordeste.model.dto;

import com.arianewelke.raizesdonordeste.entity.Unidade;

public record UnidadeResponse(Long id, String nome, String cidade) {
    public static UnidadeResponse de(Unidade u) {
        return new UnidadeResponse(u.getUnidadeId(), u.getNome(), u.getCidade());
    }
}
