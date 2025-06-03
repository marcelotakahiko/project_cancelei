package com.example.crud.service;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import jakarta.transaction.Transactional;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service // Define esta classe como um serviço gerenciado pelo Spring
public class NotificacaoVencimentoService {

    private final AssinaturaRepository assinaturaRepository;
    private final JavaMailSender mailSender;

    // Injeta o repositório de assinaturas e o componente para envio de e-mails
    public NotificacaoVencimentoService(AssinaturaRepository assinaturaRepository, JavaMailSender mailSender) {
        this.assinaturaRepository = assinaturaRepository;
        this.mailSender = mailSender;
    }

    @Transactional // Garante que o envio seja tratado como uma transação única
    public void enviarNotificacoes() {
        LocalDate amanha = LocalDate.now().plusDays(1); // Define a data de amanhã
        List<Assinatura> vencemAmanha = assinaturaRepository.findByDataVencimento(amanha); // Busca assinaturas que vencem amanhã

        for (Assinatura a : vencemAmanha) {
            // Verifica se o usuário e e-mail estão preenchidos
            if (a.getUsuario() != null && a.getUsuario().getEmail() != null) {

                // Monta a mensagem de e-mail
                SimpleMailMessage mensagem = new SimpleMailMessage();
                mensagem.setTo(a.getUsuario().getEmail()); // E-mail do destinatário
                mensagem.setSubject("Assinatura prestes a vencer - Cancelei!");
                mensagem.setText("Olá " + a.getUsuario().getNome() + ",\n\n" +
                        "Sua assinatura de \"" + a.getNomeServico() + "\" vence amanhã (" +
                        a.getDataVencimento().toString() + ").\n" +
                        "Fique atento para evitar cobranças não desejadas.\n\n" +
                        "Att,\nEquipe Cancelei!");

                mailSender.send(mensagem); // Envia o e-mail
            }
        }
    }
}
