package com.algaworks.algamoneyapi.service;

import com.algaworks.algamoneyapi.exception.PessoaInativaOuInexistenteException;
import com.algaworks.algamoneyapi.modelo.Cidade;
import com.algaworks.algamoneyapi.modelo.Endereco;
import com.algaworks.algamoneyapi.modelo.Pessoa;
import com.algaworks.algamoneyapi.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class PessoaServiceTest {
    Pessoa novaPessoa;
    Pessoa pessoaSalva;

    @Mock
    PessoaRepository pessoaRepository;

    @Mock
    Endereco endereco;

    @Mock
    Cidade novaCidade;

    @BeforeEach
    void inicializar(){
        MockitoAnnotations.openMocks(this);
        novaPessoa = criaPessoaNova();
        pessoaSalva = criaPessoaSalva();
    }

    private Endereco criaEndereco(){
        Endereco novoEndereco =  new Endereco();
        novoEndereco.setEndereco_logradouro("logradouro");
        novoEndereco.setEndereco_numero("123");
        novoEndereco.setEndereco_complemento("complemento");
        novoEndereco.setEndereco_bairro("bairro");
        novoEndereco.setEndereco_cidade(novaCidade);
        novoEndereco.setEndereco_cep("cep");
        return novoEndereco;
    }

    private Pessoa criaPessoaSalva(){
        Pessoa pessoaSalva = new Pessoa();
        pessoaSalva.setCodigo(1L);
        pessoaSalva.setNome("Pessoa Salva");
        pessoaSalva.setEndereco(endereco);
        pessoaSalva.setAtivo(Boolean.FALSE);
        return pessoaSalva;
    }

    private Pessoa criaPessoaNova(){
        Endereco enderecoNovo=criaEndereco();
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setCodigo(25L);
        novaPessoa.setNome("Nova Pessoa");
        novaPessoa.setEndereco(enderecoNovo);
        novaPessoa.setAtivo(Boolean.TRUE);
        return novaPessoa;
    }


    @Test
    void atualizarPessoa(){
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaSalva));
        pessoaService.atualizar(1L,novaPessoa);
        Mockito.verify(pessoaRepository).save(pessoaSalva);
    }

    @Test
    void atualizarAtivo(){
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        Mockito.when(pessoaRepository.findById(1L)).thenReturn(Optional.of(pessoaSalva));
        pessoaService.atualizarAtivo(1L,Boolean.TRUE);
        Mockito.verify(pessoaRepository).save(pessoaSalva);
    }

    @Test
    void salvarPessoa(){
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        pessoaService.salvar(novaPessoa);
        Mockito.verify(pessoaRepository).save(novaPessoa);
    }

    @Test
    void lancaPessoaInexistenteException(){
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        Assertions.assertThrows(PessoaInativaOuInexistenteException.class,()-> pessoaService.atualizar(1L,novaPessoa));
    }

    @Test
    void deletarPessoa(){
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        pessoaService.deletar(1L);
        Mockito.verify(pessoaRepository).deleteById(1L);
    }

    @Test
    void listarTodasPessoas(){
        PessoaService pessoaService = new PessoaService(pessoaRepository);
        pessoaService.listarTudo();
        Mockito.verify(pessoaRepository).findAll();
    }
}
