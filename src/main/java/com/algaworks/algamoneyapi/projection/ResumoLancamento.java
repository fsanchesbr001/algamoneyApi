package com.algaworks.algamoneyapi.projection;

import com.algaworks.algamoneyapi.enums.Tipo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ResumoLancamento {
    private Long codigo;
    private String descricao;
    private LocalDate dataVencimento;
    private LocalDate dataPagamento;
    private BigDecimal valor;
    private Tipo tipo;
    private String categoria;
    private String pessoa;

    public ResumoLancamento(Long codigo, String descricao, LocalDate dataVencimento, LocalDate dataPagamento,
                            BigDecimal valor, Tipo tipo, String categoria, String pessoa){
        this.codigo = codigo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
        this.valor = valor;
        this.tipo = tipo;
        this.categoria = categoria;
        this.pessoa = pessoa;
    }
}
