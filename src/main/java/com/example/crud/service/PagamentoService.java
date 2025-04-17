package com.example.crud.service;

import com.example.crud.domain.pagamento.Pagamento;
import com.example.crud.domain.pagamento.PagamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;

    public PagamentoService(PagamentoRepository repository) {
        this.repository = repository;
    }

    public List<Pagamento> listar() {
        return repository.findAll();
    }

    public Pagamento salvar(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public Pagamento buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
