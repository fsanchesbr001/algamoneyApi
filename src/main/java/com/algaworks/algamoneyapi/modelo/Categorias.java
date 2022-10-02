package com.algaworks.algamoneyapi.modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "categorias")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categorias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long codigo;

    @NotNull
    @Size(min = 3,max = 50)
    private String nome;

}