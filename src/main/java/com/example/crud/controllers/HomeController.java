package com.example.crud.controllers;

import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import com.example.crud.domain.pagamentos.Pagamento;
import com.example.crud.domain.pagamentos.PagamentoRepository;
import com.example.crud.domain.usuario.Usuario;
import com.example.crud.domain.usuario.UsuarioRepository;
import com.example.crud.domain.notificacao.Notificacao;
import com.example.crud.domain.notificacao.StatusNotificacao;
import com.example.crud.service.NotificacaoService;
import com.example.crud.dto.AssinaturaTotalDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.List;

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

        List<Assinatura> assinaturas = assinaturaRepository.findTop2ByUsuarioOrderByDataVencimentoAsc(usuario);
        List<Pagamento> pagamentos = pagamentoRepository.findTop2ByUsuario(usuario);
        List<Notificacao> notificacoes = notificacaoService.listarPorStatusEUsuario(StatusNotificacao.PENDENTE, usuario);
        List<AssinaturaTotalDTO> totaisPorAssinatura = pagamentoRepository.somarValorPorAssinatura(usuario);

        //total gasto
        BigDecimal totalPago = pagamentoRepository
                .findByAssinaturaUsuario(usuario).stream()
                .filter(p -> "Pago".equalsIgnoreCase(p.getStatus()))
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //total pendente
        BigDecimal totalPendente = pagamentoRepository
                .findByAssinaturaUsuario(usuario).stream()
                .filter(p -> "Pendente".equalsIgnoreCase(p.getStatus()))
                .map(Pagamento::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("assinaturas", assinaturas);
        model.addAttribute("pagamentos", pagamentos);
        model.addAttribute("notificacoes", notificacoes);
        model.addAttribute("totaisPorAssinatura", totaisPorAssinatura);
        model.addAttribute("totalGasto", totalPago);
        model.addAttribute("totalPendente", totalPendente);

        return "home";
    }
}
