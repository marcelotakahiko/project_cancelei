package com.example.crud.domain.pagamentos;

import com.example.crud.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("SELECT p FROM Pagamento p WHERE p.assinatura.usuario = :usuario ORDER BY p.dataPagamento DESC")
    List<Pagamento> findTop2ByUsuario(@Param("usuario") Usuario usuario);

    List<Pagamento> findByAssinaturaUsuario(Usuario usuario);

}
