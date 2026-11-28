package com.arianewelke.raizesdonordeste.model.dto;

public record EstoqueResponse(Long produtoId, String produto, Integer saldoFisico,
                              Integer reservado, Integer disponivel, Boolean ofertadoNaUnidade) {
}
