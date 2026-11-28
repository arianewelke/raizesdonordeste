package com.arianewelke.raizesdonordeste.repository;

import com.arianewelke.raizesdonordeste.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}