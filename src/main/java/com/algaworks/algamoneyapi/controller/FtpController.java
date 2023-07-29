package com.algaworks.algamoneyapi.controller;

import com.algaworks.algamoneyapi.ftp.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ftp")
public class FtpController {

    @Autowired
    private FtpService ftpService;

    @GetMapping
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_READ')")
    @ResponseStatus(HttpStatus.OK)
    public List<String> listar(){
        try {
            return ftpService.listarArquivos();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download")
    @PreAuthorize(value = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and hasAuthority('SCOPE_READ')")
    @ResponseStatus(HttpStatus.OK)
    public String baixar(@RequestBody String nomeArquivo){
        try {
            return ftpService.baixarArquivo(nomeArquivo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_WRITE')")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAnexo(@RequestParam MultipartFile anexo) throws IOException {
        ftpService.enviarArquivo(anexo);
        return "OK";
    }

    @DeleteMapping("/delete")
    @PreAuthorize(value = "hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_WRITE')")
    public ResponseEntity<String> deletar(@RequestBody String nomeArquivo){
        try {
            String status = ftpService.deletarArquivo(nomeArquivo);
            if(status.equalsIgnoreCase("OK")){
                return ResponseEntity.ok("ARQUIVO ECLUIDO COM SUCESSO");
            }
            else{
                return ResponseEntity.badRequest().body("ARQUIVO NAO ENCONTRADO");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
