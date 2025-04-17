package com.example.crud.controllers;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.service.AssinaturaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assinaturas")
public class AssinaturaRestController {

    private final AssinaturaService service;

    public AssinaturaRestController(AssinaturaService service) {
        this.service = service;
    }

    // Lista todas as assinaturas
    @GetMapping
    public List<Assinatura> listar() {
        return service.listar();
    }

    // Salva uma nova assinatura
    @PostMapping
    public Assinatura salvar(@RequestBody Assinatura assinatura) {
        return service.salvar(assinatura);
    }

    // Busca uma assinatura pelo ID
    @GetMapping("/{id}")
    public Assinatura buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElseThrow();
    }

    // Atualiza uma assinatura existente
    @PutMapping("/{id}")
    public Assinatura atualizar(@PathVariable Long id, @RequestBody Assinatura assinatura) {
        assinatura.setId(id);
        return service.salvar(assinatura);
    }

    // Exclui uma assinatura pelo ID
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
