package com.example.crud.controllers;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.service.AssinaturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assinaturas")
public class AssinaturaController {

    private final AssinaturaService service;

    public AssinaturaController(AssinaturaService service) {
        this.service = service;
    }

    // Exibe a lista de assinaturas cadastradas
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("assinaturas", service.listar());
        return "assinaturas";
    }

    // Exibe o formulário para cadastrar uma nova assinatura
    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("assinatura", new Assinatura());
        return "formAssinatura";
    }

    // Salva uma nova assinatura ou atualiza uma existente
    @PostMapping
    public String salvar(@ModelAttribute Assinatura assinatura) {
        service.salvar(assinatura);
        return "redirect:/assinaturas";
    }

    // Busca uma assinatura pelo ID e exibe no formulário para edição
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        var assinatura = service.buscarPorId(id).orElseThrow();
        model.addAttribute("assinatura", assinatura);
        return "formAssinatura";
    }

    // Exclui uma assinatura com base no ID
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/assinaturas";
    }
}
