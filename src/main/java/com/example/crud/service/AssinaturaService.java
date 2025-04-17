package com.example.crud.service;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
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
