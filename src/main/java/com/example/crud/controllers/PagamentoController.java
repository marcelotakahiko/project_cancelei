package com.example.crud.controllers;

import com.example.crud.domain.pagamentos.Pagamento;
import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.usuario.UsuarioRepository;
import com.example.crud.service.AssinaturaService;
import com.example.crud.service.PagamentoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;
    private final AssinaturaService assinaturaService;
    private final UsuarioRepository usuarioRepository;

    public PagamentoController(PagamentoService pagamentoService, AssinaturaService assinaturaService, UsuarioRepository usuarioRepository) {
        this.pagamentoService = pagamentoService;
        this.assinaturaService = assinaturaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "9") int size,
                         Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        Pageable pageable = PageRequest.of(page, size);
        Page<Pagamento> pagamentosPage = pagamentoService.listarPorUsuarioPaginado(usuario, pageable);

        model.addAttribute("page", pagamentosPage);
        return "pagamentos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("pagamento", new Pagamento());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        model.addAttribute("assinaturas", assinaturaService.listarPorUsuario(usuario));
        return "pagamentos/form";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("pagamento", new Pagamento());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        model.addAttribute("assinaturas", assinaturaService.listarPorUsuario(usuario));
        return "pagamentos/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Pagamento pagamento) {
        pagamentoService.salvar(pagamento);
        return "redirect:/pagamentos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Pagamento pagamento = pagamentoService.buscarPorId(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (!pagamento.getAssinatura().getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/pagamentos";
        }

        model.addAttribute("pagamento", pagamento);
        model.addAttribute("assinaturas", assinaturaService.listarPorUsuario(usuario));
        return "pagamentos/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.buscarPorId(id);

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (pagamento.getAssinatura().getUsuario().getId().equals(usuario.getId())) {
            pagamentoService.excluir(id);
        }

        return "redirect:/pagamentos";
    }
}
