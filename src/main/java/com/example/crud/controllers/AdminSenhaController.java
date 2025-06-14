package com.example.crud.controllers;

import com.example.crud.domain.Usuario;
import com.example.crud.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminSenhaController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/alterar-senha")
    public String exibirFormulario(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "admin/alterar-senha";
    }

    @PostMapping("/alterar-senha")
    public String alterarSenha(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam String novaSenha,
                               @RequestParam String confirmarSenha,
                               Model model) {

        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername()).orElseThrow();

        if (!novaSenha.equals(confirmarSenha)) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("erro", "As senhas não conferem.");
            return "admin/alterar-senha";
        }

        usuario.setSenha(novaSenha);
        usuarioService.atualizarSenha(usuario);
        return "redirect:/admin/usuarios";
    }
}
