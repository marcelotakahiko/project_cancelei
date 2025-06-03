package com.example.crud.service;

import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioServiceTest {

    @Test //se a senha do usuário é criptografada ao ser criada
    void deveCriarUsuarioComSenhaCriptografada() {
        // Cria versões falsas (mocks) do repositório e do encoder
        UsuarioRepository mockRepository = Mockito.mock(UsuarioRepository.class);
        PasswordEncoder mockEncoder = Mockito.mock(PasswordEncoder.class);

        // Cria o serviço sem usar construtor (injeção será manual)
        UsuarioService service = new UsuarioService();

        // Injeta os mocks nos campos privados usando Reflection
        ReflectionTestUtils.setField(service, "usuarioRepository", mockRepository);
        ReflectionTestUtils.setField(service, "passwordEncoder", mockEncoder);

        // Cria um usuário fictício para teste
        Usuario usuario = new Usuario();
        usuario.setNome("Marcelo");
        usuario.setEmail("marcelo@email.com");
        usuario.setSenha("123456");

        // Simula o comportamento do encoder e do save
        Mockito.when(mockEncoder.encode("123456")).thenReturn("senhaCriptografada");
        Mockito.when(mockRepository.save(Mockito.any())).thenAnswer(i -> i.getArgument(0));

        // Executa o método que será testado
        Usuario resultado = service.criarUsuario(usuario);

        // Verifica se a senha foi realmente criptografada
        assertEquals("senhaCriptografada", resultado.getSenha());
    }
}
