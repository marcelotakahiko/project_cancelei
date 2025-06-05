package com.example.crud.repository;

import com.example.crud.domain.Notificacao;
import com.example.crud.domain.StatusNotificacao;
import com.example.crud.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificacaoRepository extends JpaRepository<Notificacao, UUID> {
    List<Notificacao> findByStatusAndAssinaturaUsuario(StatusNotificacao status, Usuario usuario);

    List<Notificacao> findByAssinaturaUsuario(Usuario usuario);

    Page<Notificacao> findByAssinaturaUsuario(Usuario usuario, Pageable pageable);
}
