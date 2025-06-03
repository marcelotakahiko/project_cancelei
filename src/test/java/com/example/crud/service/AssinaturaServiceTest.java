package com.example.crud.service;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import com.example.crud.domain.usuario.Usuario;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssinaturaServiceTest {

    @Test //se o sistema retorna as assinaturas corretas de um usuário.
    void deveSalvarEListarAssinaturasPorUsuario() {
        // Cria mock do repositório
        AssinaturaRepository mockRepository = Mockito.mock(AssinaturaRepository.class);

        // Cria o service com o mock
        AssinaturaService service = new AssinaturaService(mockRepository);

        // Usuário fictício
        Usuario usuario = new Usuario();
        usuario.setNome("João");

        // Assinatura fictícia
        Assinatura assinatura = new Assinatura();
        assinatura.setNomeServico("Netflix");
        assinatura.setUsuario(usuario);

        // Define comportamento do mock
        Mockito.when(mockRepository.findByUsuario(usuario)).thenReturn(List.of(assinatura));

        // Executa o metodo a ser testado
        List<Assinatura> resultado = service.listarPorUsuario(usuario);

        // Verifica se o resultado contém a assinatura esperada
        assertEquals(1, resultado.size());
        assertEquals("Netflix", resultado.get(0).getNomeServico());
    }
}
