package com.example.crud.service;

import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // Indica que esta classe é um componente de serviço do Spring
public class UsuarioService {

    @Autowired // Injeta automaticamente a dependência do repositório de usuários
    private UsuarioRepository usuarioRepository;

    @Autowired // Injeta automaticamente o codificador de senhas (BCrypt, por exemplo)
    private PasswordEncoder passwordEncoder;

    // Cria um novo usuário com a senha criptografada
    public Usuario criarUsuario(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // Criptografa a senha
        return usuarioRepository.save(usuario); // Salva o usuário no banco de dados
    }

    // Lista todos os usuários cadastrados
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Busca um usuário pelo ID (UUID)
    public Usuario buscarUsuario(UUID id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id); // Busca o usuário
        return usuario.orElse(null); // Retorna o usuário ou null se não encontrado
    }

    // Atualiza os dados de um usuário existente
    public Usuario atualizarUsuario(UUID id, Usuario usuarioAtualizado) {
        Usuario usuario = buscarUsuario(id); // Busca o usuário atual pelo ID
        if (usuario != null) {
            usuario.setNome(usuarioAtualizado.getNome()); // Atualiza nome
            usuario.setEmail(usuarioAtualizado.getEmail()); // Atualiza email

            // Criptografa a nova senha antes de salvar
            usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));

            return usuarioRepository.save(usuario); // Salva as alterações
        }
        return null; // Retorna null se o usuário não for encontrado
    }

    // Deleta um usuário pelo ID
    public void deletarUsuario(UUID id) {
        usuarioRepository.deleteById(id); // Remove o usuário do banco de dados
    }
}
