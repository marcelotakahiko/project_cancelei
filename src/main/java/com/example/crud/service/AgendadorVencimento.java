package com.example.crud.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AgendadorVencimento {

    private final NotificacaoVencimentoService notificacaoVencimentoService;

    public AgendadorVencimento(NotificacaoVencimentoService notificacaoVencimentoService) {
        this.notificacaoVencimentoService = notificacaoVencimentoService;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void enviarAvisosDeVencimento() {
        notificacaoVencimentoService.enviarNotificacoes();
    }
}
