package com.example.crud.controllers;

import com.example.crud.domain.token.TokenRecuperacao;
import com.example.crud.domain.token.TokenRecuperacaoRepository;
import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.usuario.UsuarioRepository;
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

    // Exibe a tela de redefinição
    @GetMapping("/redefinir-senha")
    public String form(@RequestParam String token, Model model) {
        Optional<TokenRecuperacao> tokenRec = tokenRepository.findByToken(token);

        if (tokenRec.isEmpty() || tokenRec.get().isUsado() || tokenRec.get().getExpiracao().isBefore(LocalDateTime.now())) {
            return "redirect:/login?mensagem=Token inválido ou expirado.";
        }

        model.addAttribute("token", token);
        return "redefinir-senha";
    }

    // Processa a nova senha
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
