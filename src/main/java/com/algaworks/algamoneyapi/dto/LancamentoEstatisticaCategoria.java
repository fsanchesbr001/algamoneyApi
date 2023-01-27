package com.algaworks.algamoneyapi.dto;

import com.algaworks.algamoneyapi.modelo.Categorias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class LancamentoEstatisticaCategoria {
    private Categorias categoria;
    private BigDecimal total;

}
