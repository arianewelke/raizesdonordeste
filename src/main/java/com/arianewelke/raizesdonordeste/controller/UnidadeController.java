package com.arianewelke.raizesdonordeste.controller;

import com.arianewelke.raizesdonordeste.model.dto.CardapioItemResponse;
import com.arianewelke.raizesdonordeste.model.dto.EstoqueResponse;
import com.arianewelke.raizesdonordeste.model.dto.PaginaResponse;
import com.arianewelke.raizesdonordeste.model.dto.UnidadeResponse;
import com.arianewelke.raizesdonordeste.service.UnidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadeController {

    private final UnidadeService unidadeService;

    @GetMapping
    public PaginaResponse<UnidadeResponse> listar(@RequestParam(defaultValue = "1") int page,
                                                  @RequestParam(defaultValue = "10") int limit) {
        return unidadeService.listar(Math.max(page, 1), Math.min(Math.max(limit, 1), 100));
    }

    @GetMapping("/{id}/cardapio")
    public PaginaResponse<CardapioItemResponse> cardapio(@PathVariable Long id,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "10") int limit) {
        return unidadeService.cardapio(id, Math.max(page, 1), Math.min(Math.max(limit, 1), 100));
    }

    @GetMapping("/{id}/estoque")
    @PreAuthorize("hasAnyRole('COZINHA','GERENTE')")
    public List<EstoqueResponse> estoque(@PathVariable Long id) {
        return unidadeService.estoque(id);
    }
}
