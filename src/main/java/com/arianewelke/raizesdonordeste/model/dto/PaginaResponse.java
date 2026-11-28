package com.arianewelke.raizesdonordeste.model.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginaResponse<T>(List<T> itens, int page, int limit, long total, int totalPaginas) {
    public static <T> PaginaResponse<T> de(Page<T> pagina) {
        return new PaginaResponse<>(pagina.getContent(), pagina.getNumber() + 1,
                pagina.getSize(), pagina.getTotalElements(), pagina.getTotalPages());
    }
}
