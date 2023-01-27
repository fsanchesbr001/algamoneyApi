package com.algaworks.algamoneyapi.dto;

import com.algaworks.algamoneyapi.enums.Tipo;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class LancamentoEstatisticaPessoa {
    private Tipo tipo;
    private Pessoa pessoa;
    private BigDecimal total;
}
