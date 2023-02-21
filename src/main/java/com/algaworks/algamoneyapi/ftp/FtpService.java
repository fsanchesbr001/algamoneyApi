package com.algaworks.algamoneyapi.ftp;

import com.algaworks.algamoneyapi.property.AlgamoneyApiProperty;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FtpService {
    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

   public List<String> listarArquivos() throws IOException {
       FTPClient ftpClient = new FTPClient();
       ftpClient.connect(algamoneyApiProperty.getFtpInfo().getHost(),
               algamoneyApiProperty.getFtpInfo().getPort());
       ftpClient.login(algamoneyApiProperty.getFtpInfo().getUsername(),
               algamoneyApiProperty.getFtpInfo().getPassword());

       ftpClient.changeWorkingDirectory(algamoneyApiProperty.getFtpInfo().getDiretorioArquivos());

       String[] arq = ftpClient.listNames();

       System.out.println("Listando arquivos: \n");

       for (String f : arq) {

           System.out.println(f);
       }
       ftpClient.logout();
       ftpClient.disconnect();
       return Arrays.asList(arq);
   }

   public void enviarArquivo(MultipartFile anexo) throws IOException {

       String prefixo = UUID.randomUUID().toString();
       String arqGerado = algamoneyApiProperty.getFtpInfo().getDiretorioArquivosOrigem() +
               prefixo +"_"+anexo.getOriginalFilename();
       FileOutputStream out = new FileOutputStream(arqGerado);
       out.write(anexo.getBytes());
       out.close();

       FileInputStream in = new FileInputStream(arqGerado);

       FTPClient ftpClient = new FTPClient();

       ftpClient.connect(algamoneyApiProperty.getFtpInfo().getHost(),
               algamoneyApiProperty.getFtpInfo().getPort());
       ftpClient.login(algamoneyApiProperty.getFtpInfo().getUsername(),
               algamoneyApiProperty.getFtpInfo().getPassword());

       ftpClient.changeWorkingDirectory(algamoneyApiProperty.getFtpInfo().getDiretorioArquivos());
       ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

       if (ftpClient.storeFile (prefixo +"_"+anexo.getOriginalFilename(), in)){
           System.out.println("Arquivo armazenado com sucesso!");
       }
       else{
           System.out.println ("Erro ao armazenar o arquivo.");
       }

       ftpClient.logout();
       ftpClient.disconnect();
   }

   public String baixarArquivo(String nomeArquivo) throws IOException {
       FTPClient ftpClient = new FTPClient();
       String status = "FAILED";
       ftpClient.connect(algamoneyApiProperty.getFtpInfo().getHost(),
               algamoneyApiProperty.getFtpInfo().getPort());
       ftpClient.login(algamoneyApiProperty.getFtpInfo().getUsername(),
               algamoneyApiProperty.getFtpInfo().getPassword());

       ftpClient.changeWorkingDirectory(algamoneyApiProperty.getFtpInfo().getDiretorioArquivos());
       ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
       String[] arq = ftpClient.listNames();

       for (String f : arq) {

           if (f.equalsIgnoreCase(nomeArquivo)){
               FileOutputStream fos = new FileOutputStream(
                       algamoneyApiProperty.getFtpInfo().getDiretorioArquivosBaixados() + f);
               if(ftpClient.retrieveFile(f,fos)){
                   System.out.println("Download efetuado com sucesso!!!");
                   status="OK";
               }
               else{
                   System.out.println("Problemas no Download");
                   status="FAILED";
               }
               fos.close();
           }
       }
       ftpClient.logout();
       ftpClient.disconnect();
       return status;
   }

    public String deletarArquivo(String nomeArquivo) throws IOException {
        FTPClient ftpClient = new FTPClient();
        String status = "FILE_NOT_FOUND";
        ftpClient.connect(algamoneyApiProperty.getFtpInfo().getHost(),
                algamoneyApiProperty.getFtpInfo().getPort());
        ftpClient.login(algamoneyApiProperty.getFtpInfo().getUsername(),
                algamoneyApiProperty.getFtpInfo().getPassword());

        ftpClient.changeWorkingDirectory(algamoneyApiProperty.getFtpInfo().getDiretorioArquivos());
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        String[] arq = ftpClient.listNames();

        for (String f : arq) {

            if (f.equalsIgnoreCase(nomeArquivo)){
                if(ftpClient.deleteFile(nomeArquivo)){
                    System.out.println("Exclusao realizada com sucesso!!!");
                    status="OK";
                }
                else{
                    System.out.println("Problemas na Exclusao");
                    status="FAILED";
                }
            }
        }
        ftpClient.logout();
        ftpClient.disconnect();
        return status;
    }
}
