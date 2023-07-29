package com.algaworks.algamoneyapi.modelo;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Embeddable
public class Endereco {
    private String endereco_logradouro;
    private String endereco_numero;
    private String endereco_complemento;
    private String endereco_bairro;
    private String endereco_cep;
    @ManyToOne
    @JoinColumn(name = "codigo_cidade")
    private Cidade endereco_cidade;

}
