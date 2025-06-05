package com.example.crud.controllers;

import com.example.crud.domain.Assinatura;
import com.example.crud.repository.AssinaturaRepository;
import com.example.crud.domain.Pagamento;
import com.example.crud.repository.PagamentoRepository;
import com.example.crud.domain.Usuario;
import com.example.crud.repository.UsuarioRepository;
import com.example.crud.domain.Notificacao;
import com.example.crud.domain.StatusNotificacao;
import com.example.crud.service.NotificacaoService;
import com.example.crud.dto.AssinaturaTotalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AssinaturaRepository assinaturaRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping("/home")
    public String home(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow();

        List<Assinatura> assinaturas = assinaturaRepository.findTop3ByUsuarioOrderByDataVencimentoAsc(usuario);
        List<Pagamento> pagamentos = pagamentoRepository.findTop3ByAssinaturaUsuarioOrderByDataPagamentoDesc(usuario);
        List<Notificacao> todasNotificacoes = notificacaoService.listarPorStatusEUsuario(StatusNotificacao.PENDENTE, usuario);
        List<Notificacao> notificacoes = todasNotificacoes.stream().limit(5).collect(Collectors.toList());
        List<AssinaturaTotalDTO> totaisPorAssinatura = pagamentoRepository.somarValorPorAssinatura(usuario);

        BigDecimal totalPago = pagamentoRepository
                .findByAssinaturaUsuario(usuario).stream()
                .filter(p -> "Pago".equalsIgnoreCase(p.getStatus()))
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPendente = pagamentoRepository
                .findByAssinaturaUsuario(usuario).stream()
                .filter(p -> "Pendente".equalsIgnoreCase(p.getStatus()))
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("assinaturas", assinaturas);
        model.addAttribute("pagamentos", pagamentos);
        model.addAttribute("notificacoes", notificacoes);
        model.addAttribute("temMaisNotificacoes", todasNotificacoes.size() > 5);
        model.addAttribute("totaisPorAssinatura", totaisPorAssinatura);
        model.addAttribute("totalGasto", totalPago);
        model.addAttribute("totalPendente", totalPendente);

        return "home";
    }
}
