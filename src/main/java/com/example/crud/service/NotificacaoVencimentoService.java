package com.example.crud.service;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NotificacaoVencimentoService {

    private final AssinaturaRepository assinaturaRepository;
    private final JavaMailSender mailSender;

    public NotificacaoVencimentoService(AssinaturaRepository assinaturaRepository, JavaMailSender mailSender) {
        this.assinaturaRepository = assinaturaRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    public void enviarNotificacoes() {
        LocalDate amanha = LocalDate.now().plusDays(1);
        List<Assinatura> vencemAmanha = assinaturaRepository.findByDataVencimento(amanha);

        for (Assinatura a : vencemAmanha) {
            if (a.getUsuario() != null && a.getUsuario().getEmail() != null) {
                SimpleMailMessage mensagem = new SimpleMailMessage();
                mensagem.setTo(a.getUsuario().getEmail());
                mensagem.setSubject("Assinatura prestes a vencer - Cancelei!");
                mensagem.setText("Olá " + a.getUsuario().getNome() + ",\n\n" +
                        "Sua assinatura de \"" + a.getNomeServico() + "\" vence amanhã (" +
                        a.getDataVencimento().toString() + ").\n" +
                        "Fique atento para evitar cobranças não desejadas.\n\n" +
                        "Att,\nEquipe Cancelei!");

                mailSender.send(mensagem);
            }
        }
    }
}
