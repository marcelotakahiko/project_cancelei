package com.example.crud.controllers;

import com.example.crud.domain.Pagamento;
import com.example.crud.repository.PagamentoRepository;
import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.service.ResumoFinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class GraficosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ResumoFinanceiroService resumoFinanceiroService;

    @GetMapping("/graficos")
    public String exibirGraficos(@RequestParam(name = "mes", defaultValue = "0") int mes,
                                 @RequestParam(name = "ano", required = false) Integer ano,
                                 Model model) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        List<Pagamento> todosPagamentos = pagamentoRepository.findByAssinaturaUsuario(usuario)
                .stream()
                .filter(p -> "Pago".equalsIgnoreCase(p.getStatus()))
                .collect(Collectors.toList());

        int finalAno = (ano == null) ? LocalDate.now().getYear() : ano;

        List<Pagamento> filtrados = todosPagamentos.stream()
                .filter(p -> {
                    LocalDate data = p.getDataPagamento();
                    return (mes == 0 || data.getMonthValue() == mes) && data.getYear() == finalAno;
                })
                .collect(Collectors.toList());

        Map<String, BigDecimal> totalPorAssinatura = new LinkedHashMap<>();
        for (Pagamento p : filtrados) {
            String nome = p.getAssinatura().getNomeServico();
            totalPorAssinatura.put(nome,
                    totalPorAssinatura.getOrDefault(nome, BigDecimal.ZERO).add(p.getValor()));
        }

        Map<String, BigDecimal> totalPorMes = new TreeMap<>();
        for (Pagamento p : todosPagamentos) {
            if (p.getDataPagamento().getYear() == finalAno) {
                String chave = String.format("%02d/%d", p.getDataPagamento().getMonthValue(), finalAno);
                totalPorMes.put(chave,
                        totalPorMes.getOrDefault(chave, BigDecimal.ZERO).add(p.getValor()));
            }
        }

        model.addAttribute("anoAtual", finalAno);
        model.addAttribute("nomesAssinaturas", totalPorAssinatura.keySet());
        model.addAttribute("valoresTotais", totalPorAssinatura.values());
        model.addAttribute("meses", totalPorMes.keySet());
        model.addAttribute("valoresMensais", totalPorMes.values());

        return "graficos";
    }

    @GetMapping("/resumo-financeiro/download")
    public ResponseEntity<InputStreamResource> downloadResumoFinanceiroCSV() {
        ByteArrayInputStream csvStream = resumoFinanceiroService.gerarResumoCSV();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=resumo-financeiro.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(csvStream));
    }
}
