package com.example.crud.service;

import com.example.crud.domain.Assinatura;
import com.example.crud.domain.Pagamento;
import com.example.crud.repository.PagamentoRepository;
import com.example.crud.repository.AssinaturaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagamentoServiceTest {

    @Test //se o metodo soma os pagamentos por serviço
    void deveCalcularTotalPorAssinaturaCorretamente() {
        // Cria mocks dos repositórios necessários
        PagamentoRepository mockRepo = Mockito.mock(PagamentoRepository.class);
        AssinaturaRepository mockAssinaturaRepo = Mockito.mock(AssinaturaRepository.class);

        // Instancia o serviço com os mocks
        PagamentoService service = new PagamentoService(mockRepo, mockAssinaturaRepo);

        // Cria assinaturas fictícias (Netflix e Spotify)
        Assinatura netflix = new Assinatura();
        netflix.setNomeServico("Netflix");

        Assinatura spotify = new Assinatura();
        spotify.setNomeServico("Spotify");

        // Cria três pagamentos fictícios
        Pagamento p1 = new Pagamento();
        p1.setAssinatura(netflix);
        p1.setValor(new BigDecimal("29.90"));

        Pagamento p2 = new Pagamento();
        p2.setAssinatura(netflix);
        p2.setValor(new BigDecimal("29.90"));

        Pagamento p3 = new Pagamento();
        p3.setAssinatura(spotify);
        p3.setValor(new BigDecimal("19.90"));

        // Simula o retorno do findAll()
        List<Pagamento> pagamentos = Arrays.asList(p1, p2, p3);
        Mockito.when(mockRepo.findAll()).thenReturn(pagamentos);

        // Executa o metodo de cálculo do total por assinatura
        Map<String, BigDecimal> resultado = service.calcularTotalPorAssinatura();

        // Verifica se os totais foram calculados corretamente
        assertEquals(new BigDecimal("59.80"), resultado.get("Netflix"));
        assertEquals(new BigDecimal("19.90"), resultado.get("Spotify"));
        assertEquals(2, resultado.size()); // Verifica que só existem dois serviços somados
    }
}
