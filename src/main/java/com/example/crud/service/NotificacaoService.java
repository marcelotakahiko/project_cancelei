package com.example.crud.service;

import com.example.crud.domain.Notificacao;
import com.example.crud.domain.StatusNotificacao;
import com.example.crud.domain.Usuario;
import com.example.crud.repository.NotificacaoRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // Define que essa classe é um serviço do Spring
public class NotificacaoService {

    private final NotificacaoRepository repository;

    // Injeta o repositório via construtor
    public NotificacaoService(NotificacaoRepository repository) {
        this.repository = repository;
    }

    // Retorna todas as notificações do sistema
    public List<Notificacao> listarTodas() {
        return repository.findAll();
    }

    // Busca uma notificação pelo ID
    public Optional<Notificacao> buscarPorId(UUID id) {
        return repository.findById(id);
    }

    // Salva ou atualiza uma notificação no banco
    public void salvar(Notificacao notificacao) {
        repository.save(notificacao);
    }

    // Remove uma notificação pelo ID
    public void excluir(UUID id) {
        repository.deleteById(id);
    }

    // Lista notificações com base no status e usuário logado
    public List<Notificacao> listarPorStatusEUsuario(StatusNotificacao status, Usuario usuario) {
        return repository.findByStatusAndAssinaturaUsuario(status, usuario);
    }

    // Lista todas as notificações associadas a um usuário
    public List<Notificacao> listarPorUsuario(Usuario usuario) {
        return repository.findByAssinaturaUsuario(usuario);
    }

    // Lista as notificações do usuário com suporte à paginação
    public Page<Notificacao> listarPorUsuarioPaginado(Usuario usuario, Pageable pageable) {
        return repository.findByAssinaturaUsuario(usuario, pageable);
    }
}
