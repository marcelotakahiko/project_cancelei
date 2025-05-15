package com.example.crud.controllers;

import com.example.crud.domain.notificacao.Notificacao;
import com.example.crud.domain.notificacao.StatusNotificacao;
import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.usuario.UsuarioRepository;
import com.example.crud.service.AssinaturaService;
import com.example.crud.service.NotificacaoService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/notificacoes")
public class NotificacaoController {

    private final NotificacaoService notificacaoService;
    private final AssinaturaService assinaturaService;
    private final UsuarioRepository usuarioRepository;

    public NotificacaoController(NotificacaoService notificacaoService, AssinaturaService assinaturaService, UsuarioRepository usuarioRepository) {
        this.notificacaoService = notificacaoService;
        this.assinaturaService = assinaturaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        model.addAttribute("notificacoes", notificacaoService.listarPorUsuario(usuario));
        return "notificacoes/lista";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        model.addAttribute("notificacao", new Notificacao());
        model.addAttribute("assinaturas", assinaturaService.listarPorUsuario(usuario));
        model.addAttribute("statusList", StatusNotificacao.values());
        return "notificacoes/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute("notificacao") Notificacao notificacao) {
        notificacaoService.salvar(notificacao);
        return "redirect:/notificacoes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, Model model) {
        Notificacao notificacao = notificacaoService.buscarPorId(id).orElseThrow();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (!notificacao.getAssinatura().getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/notificacoes";
        }

        model.addAttribute("notificacao", notificacao);
        model.addAttribute("assinaturas", assinaturaService.listarPorUsuario(usuario));
        model.addAttribute("statusList", StatusNotificacao.values());
        return "notificacoes/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable UUID id) {
        Notificacao notificacao = notificacaoService.buscarPorId(id).orElseThrow();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (notificacao.getAssinatura().getUsuario().getId().equals(usuario.getId())) {
            notificacaoService.excluir(id);
        }

        return "redirect:/notificacoes";
    }
}
