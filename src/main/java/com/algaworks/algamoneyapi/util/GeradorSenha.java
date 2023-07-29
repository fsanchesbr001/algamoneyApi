package com.algaworks.algamoneyapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorSenha {
    public  String geraSenha(String senha){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }
    public static void main(String[] args) {
        GeradorSenha geradorSenha = new GeradorSenha();
        String senha = geradorSenha.geraSenha("@ngul@r0");
        System.out.println(senha);
    }
}
