package com.example.crud.service;

import com.example.crud.domain.Assinatura;
import com.example.crud.repository.AssinaturaRepository;
import com.example.crud.domain.Pagamento;
import com.example.crud.repository.PagamentoRepository;
import com.example.crud.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.YearMonth;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResumoFinanceiroService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public ByteArrayInputStream gerarResumoCSV() {
        Usuario usuario = usuarioAutenticadoService.getUsuarioLogado();

        YearMonth mesAtual = YearMonth.now();
        LocalDate inicio = mesAtual.atDay(1);
        LocalDate fim = mesAtual.atEndOfMonth();
        DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<Pagamento> pagamentos = pagamentoRepository
                .findByAssinaturaUsuarioAndStatusAndDataPagamentoBetween(usuario, "Pago", inicio, fim);

        List<Assinatura> assinaturas = assinaturaRepository
                .findByUsuarioAndStatusIgnoreCase(usuario, "Ativa");

        Map<String, Double> totalPorAssinatura = pagamentos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getAssinatura().getNomeServico(),
                        Collectors.summingDouble(p -> p.getValor().doubleValue())
                ));

        double totalGeral = pagamentos.stream()
                .mapToDouble(p -> p.getValor().doubleValue())
                .sum();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        writer.println("== RELATORIO FINANCEIRO - CANCELEI! ==");
        writer.printf("Usuario: %s%n", usuario.getNome());
        writer.printf("Periodo: %s%n", mesAtual.format(DateTimeFormatter.ofPattern("MMMM/yyyy", new Locale("pt", "BR"))));
        writer.println();

        writer.println("== PAGAMENTOS REALIZADOS ==");
        writer.println("Assinatura,Valor Pago,Data do Pagamento,Metodo de Pagamento");

        for (Pagamento pagamento : pagamentos) {
            writer.printf("%s,%.2f,%s,%s%n",
                    pagamento.getAssinatura().getNomeServico(),
                    pagamento.getValor().doubleValue(),
                    pagamento.getDataPagamento().format(formatadorData),
                    removerAcentos(pagamento.getMetodo()));
        }

        writer.println();
        writer.println("== TOTAL POR ASSINATURA ==");

        for (Map.Entry<String, Double> entry : totalPorAssinatura.entrySet()) {
            writer.printf("%s,R$ %.2f%n", entry.getKey(), entry.getValue());
        }

        writer.println();
        writer.println("== TOTAL GERAL ==");
        writer.printf("Total Gasto,R$ %.2f%n", totalGeral);

        writer.flush();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private String removerAcentos(String texto) {
        if (texto == null) return "";
        return java.text.Normalizer
                .normalize(texto, java.text.Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

}
