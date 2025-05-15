package com.example.crud.service;

import com.example.crud.domain.pagamentos.Pagamento;
import com.example.crud.domain.pagamentos.PagamentoRepository;
import com.example.crud.domain.assinatura.Assinatura;
import com.example.crud.domain.assinatura.AssinaturaRepository;
import com.example.crud.domain.usuario.Usuario;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    private final PagamentoRepository repository;
    private final AssinaturaRepository assinaturaRepository;

    public PagamentoService(PagamentoRepository repository, AssinaturaRepository assinaturaRepository) {
        this.repository = repository;
        this.assinaturaRepository = assinaturaRepository;
    }

    public List<Pagamento> listar() {
        return repository.findAll();
    }

    public List<Pagamento> listarPorUsuario(Usuario usuario) {
        return repository.findByAssinaturaUsuario(usuario);
    }

    public Pagamento salvar(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    public Pagamento buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Map<String, BigDecimal> calcularTotalPorAssinatura() {
        List<Pagamento> pagamentos = repository.findAll();
        return pagamentos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getAssinatura().getNomeServico(),
                        Collectors.mapping(Pagamento::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));
    }

    public void gerarPagamentosAutomaticos() {
        List<Assinatura> assinaturas = assinaturaRepository.findAll();

        for (Assinatura assinatura : assinaturas) {
            Pagamento p = new Pagamento();
            p.setAssinatura(assinatura);
            p.setDataPagamento(LocalDate.now());
            p.setMetodo("Automático");
            p.setStatus("Pago");
            p.setValor(assinatura.getValor());
            repository.save(p);
        }
    }
}
