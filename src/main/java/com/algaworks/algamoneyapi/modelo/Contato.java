package com.algaworks.algamoneyapi.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "contato")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long codigo;

    @NotEmpty
    private String nome;

    @Email
    @NotNull
    private String email;

    @NotEmpty
    private String telefone;


    @ManyToOne
    @JoinColumn(name = "pessoa_codigo")
    private Pessoa pessoa;
}
