package com.example.crud.controllers;

import com.example.crud.domain.Usuario;
import com.example.crud.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/usuario")
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/editarperfil")
    public String editarPerfil(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Usuario usuario = usuarioService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        model.addAttribute("usuario", usuario);
        return "editarperfil";
    }

    @PostMapping("/editarperfil")
    public String salvarEdicao(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid @ModelAttribute("usuario") Usuario usuarioAtualizado,
                               BindingResult result,
                               Model model,
                               @RequestParam(required = false) String senhaAtual,
                               @RequestParam(required = false) String novaSenha,
                               @RequestParam(required = false) String confirmarSenha) {

        Usuario atual = usuarioService.findByEmail(userDetails.getUsername()).orElseThrow();

        if (!usuarioAtualizado.getEmail().equals(atual.getEmail())
                && usuarioService.existsByEmail(usuarioAtualizado.getEmail())) {
            result.rejectValue("email", "error.email", "Este e-mail já está em uso.");
        }

        if (result.hasErrors()) {
            model.addAttribute("usuario", usuarioAtualizado);
            return "editarperfil";
        }

        atual.setNome(usuarioAtualizado.getNome());
        atual.setEmail(usuarioAtualizado.getEmail());
        atual.setCep(usuarioAtualizado.getCep());
        atual.setLogradouro(usuarioAtualizado.getLogradouro());
        atual.setCidade(usuarioAtualizado.getCidade());
        atual.setUf(usuarioAtualizado.getUf());

        if (senhaAtual != null && !senhaAtual.isBlank()
                && novaSenha != null && !novaSenha.isBlank()
                && confirmarSenha != null && novaSenha.equals(confirmarSenha)) {

            if (!passwordEncoder.matches(senhaAtual, atual.getSenha())) {
                result.rejectValue("senha", "error.senha", "Senha atual incorreta.");
                model.addAttribute("usuario", usuarioAtualizado);
                return "editarperfil";
            }

            atual.setSenha(passwordEncoder.encode(novaSenha));
            usuarioService.salvarSemAlterarSenha(atual); // A senha já foi manualmente codificada
        } else {
            usuarioService.salvarSemAlterarSenha(atual);
        }

        return "redirect:/home";
    }
}
