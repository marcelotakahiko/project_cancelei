package com.example.crud.service;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import com.example.crud.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssinaturaService {

    private final AssinaturaRepository repository;

    public AssinaturaService(AssinaturaRepository repository) {
        this.repository = repository;
    }

    public List<Assinatura> listar() {
        return repository.findAll();
    }

    public List<Assinatura> listarPorUsuario(Usuario usuario) {
        return repository.findByUsuario(usuario);
    }

    public Page<Assinatura> listarPorUsuarioPaginado(Usuario usuario, Pageable pageable) {
        return repository.findByUsuario(usuario, pageable);
    }

    public Assinatura salvar(Assinatura assinatura) {
        return repository.save(assinatura);
    }

    public Optional<Assinatura> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
