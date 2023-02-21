package com.algaworks.algamoneyapi.modelo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "anexos")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anexo {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotNull
    @Size(min = 4,max = 500)
    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @NotNull
    @Column(name = "data_upload")
    private LocalDate dataUpload;

    @NotNull
    @Size(min = 4,max = 500)
    @Column(name = "nome_arquivo_unico")
    private String nomeUnico;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "codigo_lancamento")
    @JsonIgnore
    private Lancamentos lancamento;

    @JsonIgnore
    @Transient
    private String caminhoDownload;
}
