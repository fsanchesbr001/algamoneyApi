package com.algaworks.algamoneyapi.mail;

import com.algaworks.algamoneyapi.modelo.Lancamentos;
import com.algaworks.algamoneyapi.modelo.Usuario;
import com.algaworks.algamoneyapi.repository.LancamentoRepository;
import com.algaworks.algamoneyapi.repository.UsuarioRepository;
import com.algaworks.algamoneyapi.service.LancamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Mailer {

    @Autowired
    private JavaMailSender javaMailSender;

   @Autowired
    private TemplateEngine thymeleaf;

    @Autowired
    LancamentoRepository lancamentoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    private static final String ROLE_DESTINATARIO ="ROLE_PESQUISAR_LANCAMENTO";

    private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);

    public void avisoLancamentosVencidos(){
        logger.debug("debug Ativo - Inicio método avisoLancamentosVencidos()");
        logger.info("Inicio método avisoLancamentosVencidos");
        logger.info("Configurando dados para envio de aviso");
        String template = "mail/aviso-lancamentos-vencidos";

        List<Lancamentos> listaVencidos = lancamentoRepository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
        if(listaVencidos.isEmpty()){
            logger.debug("debug Ativo - Nenhum Lançamento vencido. Não será enviado e-mail.");
            logger.debug("debug Ativo - Data Pesquisada:{}",LocalDate.now());
            logger.info("Nenhum Lançamento vencido. Não será enviado e-mail.");
            return;
        }
        logger.info("{} lançamentos vencidos encontrados. E-mail será enviado",listaVencidos.size());

        List<Usuario> usuarios = usuarioRepository.findByPermissaoListDescricao(ROLE_DESTINATARIO);
        if(usuarios.isEmpty()){
            logger.info("Nenhum destinatário encontrado. Não será enviado e-mail.");
            return;
        }
        List<String> listaEmails = usuarios.stream().map(usuario -> usuario.getEmail()).collect(Collectors.toList());
        logger.info("{} destinatário(s) encontrado(s). E-mail será enviado.",listaEmails.size());

        Map<String,Object> variaveis = new HashMap<>();
        variaveis.put("lancamentos",listaVencidos);

        this.enviarEmail("aviso@algamoney.fabriciosanches.com", listaEmails,"Algamoney - Lancamentos Vencidos",
                template,variaveis);
        logger.info("Email enviado!!!!!");
        logger.debug("debug Ativo - Fim método avisoLancamentosVencidos()");
        logger.info("Fim método avisoLancamentosVencidos");
    }

    public void enviarEmail(String remetente, List<String> destinatario, String assunto, String mensagem){


        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"UTF-8");

            helper.setFrom(remetente);
            helper.setTo(destinatario.toArray(new String[destinatario.size()]));
            helper.setSubject(assunto);
            helper.setText(mensagem,true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Problemas no envio de e-mail",e);
        }
    }

    public void enviarEmail(String remetente, List<String> destinatario, String assunto,
                            String template, Map<String,Object> variaveis) {

        Context context = new Context(new Locale("pt", "BR"));
        variaveis.entrySet().forEach(e -> context.setVariable(e.getKey(), e.getValue()));
        String mensagem = thymeleaf.process(template, context);
        this.enviarEmail(remetente, destinatario, assunto, mensagem);
    }
}
