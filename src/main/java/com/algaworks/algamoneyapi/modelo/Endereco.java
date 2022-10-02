package com.algaworks.algamoneyapi.modelo;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Endereco {
    private String endereco_logradouro;
    private String endereco_numero;
    private String endereco_complemento;
    private String endereco_bairro;
    private String endereco_cidade;
    private String endereco_estado;
    private String endereco_cep;
}
