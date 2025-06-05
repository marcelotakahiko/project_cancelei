package com.example.crud.service;

import com.example.crud.domain.Pagamento;
import com.example.crud.repository.PagamentoRepository;
import com.example.crud.domain.Assinatura;
import com.example.crud.repository.AssinaturaRepository;
import com.example.crud.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service // Indica que esta classe é um serviço gerenciado pelo Spring
public class PagamentoService {

    private final PagamentoRepository repository;
    private final AssinaturaRepository assinaturaRepository;

    // Injeta as dependências via construtor
    public PagamentoService(PagamentoRepository repository, AssinaturaRepository assinaturaRepository) {
        this.repository = repository;
        this.assinaturaRepository = assinaturaRepository;
    }

    // Lista todos os pagamentos do sistema
    public List<Pagamento> listar() {
        return repository.findAll();
    }

    // Lista os pagamentos associados a um determinado usuário
    public List<Pagamento> listarPorUsuario(Usuario usuario) {
        return repository.findByAssinaturaUsuario(usuario);
    }

    // Lista os pagamentos de um usuário com paginação
    public Page<Pagamento> listarPorUsuarioPaginado(Usuario usuario, Pageable pageable) {
        return repository.findByAssinaturaUsuario(usuario, pageable);
    }

    // Salva um novo pagamento no banco de dados
    public Pagamento salvar(Pagamento pagamento) {
        return repository.save(pagamento);
    }

    // Busca um pagamento pelo ID
    public Pagamento buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(); // Lança exceção se não encontrar
    }

    // Exclui um pagamento pelo ID
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    // Calcula o valor total gasto por nome de assinatura (ex: Netflix, Spotify)
    public Map<String, BigDecimal> calcularTotalPorAssinatura() {
        List<Pagamento> pagamentos = repository.findAll();
        return pagamentos.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getAssinatura().getNomeServico(), // Agrupa pelo nome do serviço
                        Collectors.mapping(Pagamento::getValor,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)) // Soma os valores
                ));
    }

    // Gera pagamentos automáticos com status "Pago" para todas as assinaturas do sistema
    public void gerarPagamentosAutomaticos() {
        List<Assinatura> assinaturas = assinaturaRepository.findAll();

        for (Assinatura assinatura : assinaturas) {
            Pagamento p = new Pagamento();
            p.setAssinatura(assinatura);
            p.setDataPagamento(LocalDate.now()); // Data atual
            p.setMetodo("Automático"); // Tipo de pagamento
            p.setStatus("Pago"); // Status fixo
            p.setValor(assinatura.getValor()); // Valor da assinatura
            repository.save(p); // Salva o pagamento
        }
    }
}
