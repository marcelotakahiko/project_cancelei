package com.example.crud.controllers;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.usuario.UsuarioRepository;
import com.example.crud.service.AssinaturaService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assinaturas")
public class AssinaturaController {

    private final AssinaturaService service;
    private final UsuarioRepository usuarioRepository;

    public AssinaturaController(AssinaturaService service, UsuarioRepository usuarioRepository) {
        this.service = service;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listar(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        model.addAttribute("assinaturas", service.listarPorUsuario(usuario));
        return "assinaturas";
    }

    @GetMapping("/nova")
    public String nova(Model model) {
        model.addAttribute("assinatura", new Assinatura());
        return "formAssinatura";
    }

    @PostMapping
    public String salvar(@ModelAttribute Assinatura assinatura) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        assinatura.setUsuario(usuario); // Associa o usuário logado
        service.salvar(assinatura);

        return "redirect:/assinaturas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Assinatura assinatura = service.buscarPorId(id).orElseThrow();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (!assinatura.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/assinaturas"; // Proteção extra
        }

        model.addAttribute("assinatura", assinatura);
        return "formAssinatura";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        Assinatura assinatura = service.buscarPorId(id).orElseThrow();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (assinatura.getUsuario().getId().equals(usuario.getId())) {
            service.excluir(id);
        }

        return "redirect:/assinaturas";
    }
}
