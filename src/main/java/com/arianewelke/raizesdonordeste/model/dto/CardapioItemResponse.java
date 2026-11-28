package com.arianewelke.raizesdonordeste.model.dto;

import java.math.BigDecimal;

public record CardapioItemResponse(Long produtoId, String nome, String descricao,
                                   BigDecimal precoBase, Integer disponivel) {
}
