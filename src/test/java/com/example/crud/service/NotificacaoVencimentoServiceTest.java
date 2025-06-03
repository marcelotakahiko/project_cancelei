package com.example.crud.service;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import com.example.crud.domain.usuario.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificacaoVencimentoServiceTest {

    @Test //se o sistema envia email para assinaturas que vencem amanhã
    void deveEnviarEmailParaAssinaturasQueVencemAmanha() {
        // Mock dos repositórios e sender
        AssinaturaRepository mockRepo = Mockito.mock(AssinaturaRepository.class);
        JavaMailSender mockMailSender = Mockito.mock(JavaMailSender.class);

        NotificacaoVencimentoService service = new NotificacaoVencimentoService(mockRepo, mockMailSender);

        // Cria assinatura que vence amanhã
        Usuario usuario = new Usuario();
        usuario.setNome("Marcelo");
        usuario.setEmail("marcelo@email.com");

        Assinatura assinatura = new Assinatura();
        assinatura.setUsuario(usuario);
        assinatura.setNomeServico("Netflix");
        assinatura.setDataVencimento(LocalDate.now().plusDays(1));

        Mockito.when(mockRepo.findByDataVencimento(LocalDate.now().plusDays(1)))
                .thenReturn(List.of(assinatura));

        // Captura o e-mail enviado
        ArgumentCaptor<SimpleMailMessage> emailCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // Executa
        service.enviarNotificacoes();

        // Verifica se o email foi enviado corretamente
        Mockito.verify(mockMailSender).send(emailCaptor.capture());
        SimpleMailMessage email = emailCaptor.getValue();

        assertEquals("marcelo@email.com", email.getTo()[0]);
        assertEquals("Assinatura prestes a vencer - Cancelei!", email.getSubject());
        assertEquals(true, email.getText().contains("Netflix"));
    }
}
