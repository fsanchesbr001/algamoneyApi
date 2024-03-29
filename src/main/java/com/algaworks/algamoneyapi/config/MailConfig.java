package com.algaworks.algamoneyapi.config;

import com.algaworks.algamoneyapi.property.AlgamoneyApiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@Configuration
@EnableScheduling
public class MailConfig {

    @Autowired
    private AlgamoneyApiProperty algamoneyApiProperty;

    @Bean
    public JavaMailSender javaMailSender(){
        Properties properties = new Properties();
        properties.put("mail.transport.protocol","smtp");
        properties.put("mail.smtp.starttls.enable",false);
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.connectiontimeout",10000);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setJavaMailProperties(properties);
        mailSender.setHost(algamoneyApiProperty.getMail().getHost());
        mailSender.setPort(algamoneyApiProperty.getMail().getPort());
        mailSender.setUsername(algamoneyApiProperty.getMail().getUsername());
        mailSender.setPassword(algamoneyApiProperty.getMail().getPassword());

        return mailSender;
    }
}
