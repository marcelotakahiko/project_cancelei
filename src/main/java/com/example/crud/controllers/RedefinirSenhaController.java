package com.example.crud.controllers;

import com.example.crud.domain.TokenRecuperacao;
import com.example.crud.repository.TokenRecuperacaoRepository;
import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class RedefinirSenhaController {

    @Autowired
    private TokenRecuperacaoRepository tokenRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/redefinir-senha")
    public String form(@RequestParam String token, Model model) {
        Optional<TokenRecuperacao> tokenRec = tokenRepository.findByToken(token);

        if (tokenRec.isEmpty() || tokenRec.get().isUsado() || tokenRec.get().getExpiracao().isBefore(LocalDateTime.now())) {
            return "redirect:/login?mensagem=Token inválido ou expirado.";
        }

        model.addAttribute("token", token);
        return "redefinir-senha";
    }

    @PostMapping("/redefinir-senha")
    @Transactional
    public String salvar(@RequestParam String token,
                         @RequestParam String novaSenha,
                         @RequestParam String confirmacaoSenha) {

        Optional<TokenRecuperacao> tokenRec = tokenRepository.findByToken(token);

        if (tokenRec.isEmpty() || tokenRec.get().isUsado() || tokenRec.get().getExpiracao().isBefore(LocalDateTime.now())) {
            return "redirect:/login?mensagem=Token inválido ou expirado.";
        }

        if (!novaSenha.equals(confirmacaoSenha)) {
            return "redirect:/redefinir-senha?token=" + token + "&erro=Senhas não coincidem";
        }

        TokenRecuperacao tokenObj = tokenRec.get();
        Usuario usuario = tokenObj.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        tokenObj.setUsado(true);
        tokenRepository.save(tokenObj);

        return "redirect:/login?mensagem=Senha redefinida com sucesso.";
    }
}
