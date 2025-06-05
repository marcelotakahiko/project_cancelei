package com.example.crud.controllers;

import com.example.crud.domain.TokenRecuperacao;
import com.example.crud.repository.TokenRecuperacaoRepository;
import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class RecuperacaoSenhaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenRecuperacaoRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @GetMapping("/esqueci-senha")
    public String form() {
        return "esqueci-senha";
    }


    @PostMapping("/esqueci-senha")
    public String enviarLink(@RequestParam String email, HttpServletRequest request) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            TokenRecuperacao token = new TokenRecuperacao();
            token.setUsuario(usuario);
            tokenRepository.save(token);

            String url = request.getRequestURL().toString().replace(request.getRequestURI(), "") +
                    "/redefinir-senha?token=" + token.getToken();

            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(email);
            mensagem.setSubject("Cancelei! - Redefinição de Senha");
            mensagem.setText("Clique no link abaixo para redefinir sua senha:\n" + url);

            mailSender.send(mensagem);
        }

        return "redirect:/login?mensagem=Se o e-mail estiver correto, o link foi enviado.";
    }
}
