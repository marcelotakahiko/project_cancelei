package com.example.crud.domain.token;

import com.example.crud.domain.usuario.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tokens_recuperacao")
public class TokenRecuperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiracao;

    private boolean usado = false;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public TokenRecuperacao() {
        this.token = UUID.randomUUID().toString();
        this.expiracao = LocalDateTime.now().plusMinutes(30); // válido por 30 minutos
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiracao() {
        return expiracao;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
