package com.example.crud.domain.notificacao;

import com.example.crud.domain.assinatura.Assinatura;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
public class Notificacao {

    @Id
    @GeneratedValue
    private UUID id;

    private String titulo;

    private String mensagem;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private StatusNotificacao status;

    @ManyToOne
    @JoinColumn(name = "assinatura_id")
    private Assinatura assinatura;
}
