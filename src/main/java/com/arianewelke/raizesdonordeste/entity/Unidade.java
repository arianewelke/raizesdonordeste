package com.arianewelke.raizesdonordeste.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "unidade")
@Getter
@Setter
public class Unidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unidade_id")
    private Long unidadeId;

    private String nome;
    private String cidade;
    private boolean ativa;
}
