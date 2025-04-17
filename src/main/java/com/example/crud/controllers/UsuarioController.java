package com.example.crud.controllers;

import com.example.crud.domain.usuario.Usuario;
import com.example.crud.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Cria um novo usuário
    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario) {
        return usuarioService.criarUsuario(usuario);
    }

    // Lista todos os usuários
    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // Busca um usuário por ID
    @GetMapping("/{id}")
    public Usuario buscarUsuario(@PathVariable UUID id) {
        return usuarioService.buscarUsuario(id);
    }

    // Atualiza os dados de um usuário existente
    @PutMapping("/{id}")
    public Usuario atualizarUsuario(@PathVariable UUID id, @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(id, usuario);
    }

    // Deleta um usuário por ID
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
    }
}
