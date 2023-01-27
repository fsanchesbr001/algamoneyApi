package com.algaworks.algamoneyapi.dto;

import com.algaworks.algamoneyapi.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LancamentoEstatisticaDia {
    private Tipo tipo;
    private LocalDate dia;
    private BigDecimal total;
}
