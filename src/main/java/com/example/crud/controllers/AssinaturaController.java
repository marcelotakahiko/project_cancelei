package com.example.crud.controllers;

import com.example.crud.domain.Assinatura;
import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.AssinaturaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String listar(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "9") int size,
                         Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        Pageable pageable = PageRequest.of(page, size);
        Page<Assinatura> assinaturasPage = service.listarPorUsuarioPaginado(usuario, pageable);

        model.addAttribute("page", assinaturasPage);
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

        assinatura.setUsuario(usuario);
        service.salvar(assinatura);

        return "redirect:/assinaturas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Assinatura assinatura = service.buscarPorId(id).orElseThrow();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (!assinatura.getUsuario().getId().equals(usuario.getId())) {
            return "redirect:/assinaturas";
        }

        model.addAttribute("assinatura", assinatura);
        return "formAssinatura";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Assinatura assinatura = service.buscarPorId(id).orElseThrow();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        if (!assinatura.getUsuario().getId().equals(usuario.getId())) {
            redirectAttributes.addFlashAttribute("erro", "Você não tem permissão para excluir esta assinatura.");
            return "redirect:/assinaturas";
        }

        try {
            service.excluir(id);
            redirectAttributes.addFlashAttribute("mensagem", "Assinatura excluída com sucesso.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não foi possível excluir: existem pagamentos vinculados.");
        }

        return "redirect:/assinaturas";
    }
}
