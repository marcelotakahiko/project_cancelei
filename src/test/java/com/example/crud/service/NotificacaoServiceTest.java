package com.example.crud.service;

import com.example.crud.domain.Notificacao;
import com.example.crud.repository.NotificacaoRepository;
import com.example.crud.domain.StatusNotificacao;
import com.example.crud.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificacaoServiceTest {

    @Test //se o sistema busca notificações por status (pendente) e por usuário
    void deveListarNotificacoesPorStatusEUsuario() {
        // Mock do repositório
        NotificacaoRepository mockRepo = Mockito.mock(NotificacaoRepository.class);

        // Instância do service
        NotificacaoService service = new NotificacaoService(mockRepo);

        // Dados simulados
        Usuario usuario = new Usuario();
        usuario.setNome("Marcelo");

        Notificacao n1 = new Notificacao();
        n1.setMensagem("Sua assinatura vai vencer amanhã");

        StatusNotificacao status = StatusNotificacao.PENDENTE;

        // Configura mock
        Mockito.when(mockRepo.findByStatusAndAssinaturaUsuario(status, usuario))
                .thenReturn(List.of(n1));

        // Executa
        List<Notificacao> resultado = service.listarPorStatusEUsuario(status, usuario);

        // Verifica
        assertEquals(1, resultado.size());
        assertEquals("Sua assinatura vai vencer amanhã", resultado.get(0).getMensagem());
    }
}
