package com.example.crud.service;

import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioAutenticadoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario getUsuarioLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByEmail(email).orElseThrow();
    }
}
