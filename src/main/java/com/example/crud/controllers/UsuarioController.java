package com.example.crud.controllers;

import com.example.crud.domain.usuario.Usuario;
import com.example.crud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.UUID;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Usuario usuario) {
        usuarioService.criarUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable UUID id, Model model) {
        Usuario usuario = usuarioService.buscarUsuario(id);
        model.addAttribute("usuario", usuario);
        return "usuarios/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return "redirect:/usuarios";
    }
}
