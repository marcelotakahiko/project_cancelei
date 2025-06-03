package com.example.crud.service;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import com.example.crud.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Define que esta classe é um serviço Spring (lógica de negócio)
public class AssinaturaService {

    private final AssinaturaRepository repository;

    // Injeta o repositório de assinaturas via construtor
    public AssinaturaService(AssinaturaRepository repository) {
        this.repository = repository;
    }

    // Lista todas as assinaturas do sistema
    public List<Assinatura> listar() {
        return repository.findAll();
    }

    // Lista todas as assinaturas associadas a um usuário
    public List<Assinatura> listarPorUsuario(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }

    // Lista as assinaturas do usuário com paginação
    public Page<Assinatura> listarPorUsuarioPaginado(Usuario usuario, Pageable pageable) {
        return repository.findByUsuario(usuario, pageable);
    }

    // Salva ou atualiza uma assinatura no banco de dados
    public Assinatura salvar(Assinatura assinatura) {
        return repository.save(assinatura);
    }

    // Busca uma assinatura específica pelo ID
    public Optional<Assinatura> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Exclui uma assinatura pelo ID
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
