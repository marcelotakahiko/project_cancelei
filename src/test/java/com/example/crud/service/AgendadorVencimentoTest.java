package com.example.crud.service;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AgendadorVencimentoTest {

    @Test // Verifica se o metodo agendado chama o envio de notificações corretamente
    void deveChamarEnvioDeNotificacoes() {
        // Cria um mock do serviço de notificação
        NotificacaoVencimentoService mockService = mock(NotificacaoVencimentoService.class);

        // Instancia o agendador com o mock
        AgendadorVencimento agendador = new AgendadorVencimento(mockService);

        // Executa manualmente o metodo agendado
        agendador.enviarAvisosDeVencimento();

        // Verifica se o metodo enviarNotificacoes foi chamado
        verify(mockService).enviarNotificacoes();
    }
}
