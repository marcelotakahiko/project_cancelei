package com.example.crud.repository;

import com.example.crud.domain.Pagamento;
import com.example.crud.domain.Usuario;
import com.example.crud.dto.AssinaturaTotalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    List<Pagamento> findTop3ByAssinaturaUsuarioOrderByDataPagamentoDesc(Usuario usuario);

    List<Pagamento> findByAssinaturaUsuario(Usuario usuario);

    Page<Pagamento> findByAssinaturaUsuario(Usuario usuario, Pageable pageable);

    @org.springframework.data.jpa.repository.Query(
            "SELECT new com.example.crud.dto.AssinaturaTotalDTO(p.assinatura.nomeServico, SUM(p.valor)) " +
                    "FROM Pagamento p WHERE p.assinatura.usuario = :usuario GROUP BY p.assinatura.nomeServico"
    )
    List<AssinaturaTotalDTO> somarValorPorAssinatura(@Param("usuario") Usuario usuario);

    List<Pagamento> findByAssinaturaUsuarioAndStatusAndDataPagamentoBetween(Usuario usuario, String status, LocalDate inicio, LocalDate fim);
}
