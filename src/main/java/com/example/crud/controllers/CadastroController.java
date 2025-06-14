package com.example.crud.controllers;

import com.example.crud.domain.Usuario;
import com.example.crud.security.Role;
import com.example.crud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class CadastroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String form(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String salvar(
            @Valid @ModelAttribute("usuario") Usuario usuario,
            BindingResult bindingResult,
            Model model) {

        if (usuarioService.existsByEmail(usuario.getEmail())) {
            bindingResult.rejectValue("email",
                    "error.email",
                    "Este e-mail já está em uso.");
        }

        if (!usuario.getSenha().equals(usuario.getConfirmarSenha())) {
            bindingResult.rejectValue("confirmarSenha",
                    "error.confirmarSenha",
                    "As senhas não conferem.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "cadastro";
        }

        usuario.setRole(Role.USER);
        usuarioService.criarUsuario(usuario);
        return "redirect:/login";
    }
}
