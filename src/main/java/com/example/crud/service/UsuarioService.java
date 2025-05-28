package com.example.crud.service;

import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario criarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuario(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public Usuario atualizarUsuario(UUID id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarUsuario(id);
        if (usuario != null) {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());

            // criptografa
            usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));

            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id);
    }
}
