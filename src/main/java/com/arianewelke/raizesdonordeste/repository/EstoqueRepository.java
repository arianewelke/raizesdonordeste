package com.arianewelke.raizesdonordeste.repository;

import com.arianewelke.raizesdonordeste.entity.Estoque;
import com.arianewelke.raizesdonordeste.model.dto.CardapioItemResponse;
import com.arianewelke.raizesdonordeste.model.dto.EstoqueResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    @Query("""
            select new com.arianewelke.raizesdonordeste.model.dto.CardapioItemResponse(
                p.produtoId, p.nome, p.descricao, p.precoBase, (e.saldoFisico - e.reservado))
            from Estoque e join e.produto p
            where e.unidade.unidadeId = :unidadeId
              and e.ofertadoNaUnidade = true
              and p.ativo = true
              and (e.saldoFisico - e.reservado) > 0
            order by p.nome
            """)
    Page<CardapioItemResponse> findCardapio(@Param("unidadeId") Long unidadeId, Pageable pageable);

    @Query("""
            select new com.arianewelke.raizesdonordeste.model.dto.EstoqueResponse(
                p.produtoId, p.nome, e.saldoFisico, e.reservado,
                (e.saldoFisico - e.reservado), e.ofertadoNaUnidade)
            from Estoque e join e.produto p
            where e.unidade.unidadeId = :unidadeId
            order by p.nome
            """)
    List<EstoqueResponse> findEstoqueDaUnidade(@Param("unidadeId") Long unidadeId);
}
