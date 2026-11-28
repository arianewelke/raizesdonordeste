package com.arianewelke.raizesdonordeste.service;

import com.arianewelke.raizesdonordeste.entity.Unidade;
import com.arianewelke.raizesdonordeste.exception.ApiException;
import com.arianewelke.raizesdonordeste.model.dto.CardapioItemResponse;
import com.arianewelke.raizesdonordeste.model.dto.EstoqueResponse;
import com.arianewelke.raizesdonordeste.model.dto.PaginaResponse;
import com.arianewelke.raizesdonordeste.model.dto.UnidadeResponse;
import com.arianewelke.raizesdonordeste.repository.EstoqueRepository;
import com.arianewelke.raizesdonordeste.repository.UnidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;
    private final EstoqueRepository estoqueRepository;

    @Transactional(readOnly = true)
    public PaginaResponse<UnidadeResponse> listar(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("nome"));
        return PaginaResponse.de(unidadeRepository.findByAtivaTrue(pageable).map(UnidadeResponse::de));
    }

    @Transactional(readOnly = true)
    public PaginaResponse<CardapioItemResponse> cardapio(Long unidadeId, int page, int limit) {
        buscarUnidadeAtiva(unidadeId);
        return PaginaResponse.de(estoqueRepository.findCardapio(unidadeId, PageRequest.of(page - 1, limit)));
    }

    @Transactional(readOnly = true)
    public List<EstoqueResponse> estoque(Long unidadeId) {
        buscarUnidadeAtiva(unidadeId);
        return estoqueRepository.findEstoqueDaUnidade(unidadeId);
    }

    private Unidade buscarUnidadeAtiva(Long id) {
        return unidadeRepository.findById(id)
                .filter(Unidade::isAtiva)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "RECURSO_NAO_ENCONTRADO", "Unidade não encontrada."));
    }
}
