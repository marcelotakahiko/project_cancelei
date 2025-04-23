package com.example.crud.controllers;

import com.example.crud.domain.pagamentos.Pagamento;
import com.example.crud.service.PagamentoService;
import com.example.crud.service.AssinaturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final AssinaturaService assinaturaService;

    public PagamentoController(PagamentoService pagamentoService, AssinaturaService assinaturaService) {
        this.pagamentoService = pagamentoService;
        this.assinaturaService = assinaturaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pagamentos", pagamentoService.listar());
        return "pagamentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pagamento", new Pagamento());
        model.addAttribute("assinaturas", assinaturaService.listar());
        return "pagamentos/form";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("pagamento", new Pagamento());
        model.addAttribute("assinaturas", assinaturaService.listar());
        return "pagamentos/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Pagamento pagamento) {
        pagamentoService.salvar(pagamento);
        return "redirect:/pagamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pagamento", pagamentoService.buscarPorId(id));
        model.addAttribute("assinaturas", assinaturaService.listar());
        return "pagamentos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        pagamentoService.excluir(id);
        return "redirect:/pagamentos";
    }
}
