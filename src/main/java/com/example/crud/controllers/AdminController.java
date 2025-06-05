package com.example.crud.controllers;

import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("")
    public String redirecionarParaUsuarios() {
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarForm(@PathVariable UUID id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "admin/editar";
    }

    @PostMapping("/usuarios/editar")
    public String salvarEdicao(@ModelAttribute Usuario usuario) {
        Usuario existente = usuarioRepository.findById(usuario.getId()).orElseThrow();

        usuario.setSenha(existente.getSenha());
        usuario.setActive(existente.getActive());
        usuario.setAceitouTermos(existente.getAceitouTermos());
        usuario.setCep(existente.getCep());
        usuario.setLogradouro(existente.getLogradouro());
        usuario.setBairro(existente.getBairro());
        usuario.setCidade(existente.getCidade());
        usuario.setUf(existente.getUf());

        usuarioRepository.save(usuario);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/excluir/{id}")
    public String excluir(@PathVariable UUID id) {
        usuarioRepository.deleteById(id);
        return "redirect:/admin/usuarios";
    }
}
