package com.example.crud.domain.pagamentos;

import com.example.crud.domain.usuario.Usuario;
import com.example.crud.dto.AssinaturaTotalDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT p FROM Pagamento p WHERE p.assinatura.usuario = :usuario ORDER BY p.dataPagamento DESC")
    List<Pagamento> findTop2ByUsuario(@Param("usuario") Usuario usuario);

    List<Pagamento> findByAssinaturaUsuario(Usuario usuario);

    @Query("SELECT new com.example.crud.dto.AssinaturaTotalDTO(p.assinatura.nomeServico, SUM(p.valor)) " +
            "FROM Pagamento p WHERE p.assinatura.usuario = :usuario GROUP BY p.assinatura.nomeServico")
    List<AssinaturaTotalDTO> somarValorPorAssinatura(@Param("usuario") Usuario usuario);

}
