package com.example.crud.service;

import com.example.crud.domain.notificacao.Notificacao;
import com.example.crud.domain.notificacao.StatusNotificacao;
import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.notificacao.NotificacaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificacaoService {

    private final NotificacaoRepository repository;

    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    public List<Notificacao> listarTodas() {
        return repository.findAll();
    }

    public Optional<Notificacao> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    public void salvar(Notificacao notificacao) {
        repository.save(notificacao);
    }

    public void excluir(UUID id) {
        repository.deleteById(id);
    }

    public List<Notificacao> listarPorStatusEUsuario(StatusNotificacao status, Usuario usuario) {
        return repository.findByStatusAndAssinaturaUsuario(status, usuario);
    }

    public List<Notificacao> listarPorUsuario(Usuario usuario) {
        return repository.findByAssinaturaUsuario(usuario);
    }
}
