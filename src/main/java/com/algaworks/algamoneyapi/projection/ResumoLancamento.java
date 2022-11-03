package com.algaworks.algamoneyapi.projection;

import com.algaworks.algamoneyapi.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class ResumoLancamento {
    private Long codigo;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private Tipo tipo;
    private String categoria;
    private String pessoa;
}
