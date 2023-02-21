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
    private final Seguranca seguranca = new Seguranca();
    private  final Mail mail = new Mail();
    private final FtpInfo ftpInfo = new FtpInfo();

    @Getter
    @Setter
    public static class Seguranca{
        private boolean isEnableHttps;
    }

    @Getter
    @Setter
    public  static class Mail{
        private String host;
        private Integer port;
        private String username;
        private String password;
    }
    @Getter
    @Setter
    public static class FtpInfo{
        private String host;
        private Integer port;
        private String username;
        private String password;
        private String diretorioArquivos;
        private String diretorioArquivosOrigem;
        private String diretorioArquivosBaixados;
    }
}
