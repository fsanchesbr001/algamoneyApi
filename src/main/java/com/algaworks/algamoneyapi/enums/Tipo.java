package com.algaworks.algamoneyapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Tipo {
    RECEITA("Receita"),
    DESPESA("Despesa");

    @Getter
    private final String descricao;
}
