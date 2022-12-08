package com.algaworks.algamoneyapi.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {

    private String originAllowed = "http://localhost:4200";
    private  Seguranca seguranca = new Seguranca();

    @Getter
    @Setter
    public static class Seguranca{
        private boolean isEnableHttps;
    }
}
