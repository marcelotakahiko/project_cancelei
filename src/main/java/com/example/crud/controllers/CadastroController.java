package com.example.crud.controllers;

import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CadastroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // 👈 agora sim

    @GetMapping("/cadastro")
    public String form(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String salvar(@ModelAttribute Usuario usuario) {
        usuario.setRole(Role.USER);  // sempre começa como USER
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));  // 👈 codifica antes de salvar
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }
}
