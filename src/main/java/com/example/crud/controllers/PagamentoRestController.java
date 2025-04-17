package com.example.crud.controllers;

import com.example.crud.domain.pagamento.Pagamento;
import com.example.crud.service.PagamentoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoRestController {

    private final PagamentoService service;

    public PagamentoRestController(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pagamento> listar() {
        return service.listar();
    }

    @PostMapping
    public Pagamento salvar(@RequestBody Pagamento pagamento) {
        return service.salvar(pagamento);
    }

    @GetMapping("/{id}")
    public Pagamento buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Pagamento atualizar(@PathVariable Long id, @RequestBody Pagamento novo) {
        Pagamento existente = service.buscarPorId(id);
        existente.setValor(novo.getValor());
        existente.setMetodo(novo.getMetodo());
        existente.setDataPagamento(novo.getDataPagamento());
        existente.setStatus(novo.getStatus());
        existente.setAssinaturaId(novo.getAssinaturaId());
        return service.salvar(existente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
