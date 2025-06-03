package com.example.crud.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // Define esta classe como um componente do Spring que será gerenciado automaticamente

public class AgendadorVencimento {

    private final NotificacaoVencimentoService notificacaoVencimentoService;

    // Injeta o serviço responsável por enviar as notificações de vencimento
    public AgendadorVencimento(NotificacaoVencimentoService notificacaoVencimentoService) {
        this.notificacaoVencimentoService = notificacaoVencimentoService;
    }

    // Metodo agendado para rodar todos os dias às 08:00 da manhã
    @Scheduled(cron = "0 0 8 * * *") // Formato: segundos minutos horas dia mês dia-da-semana
    public void enviarAvisosDeVencimento() {
        notificacaoVencimentoService.enviarNotificacoes(); // Chama o serviço para enviar e-mails
    }
}
