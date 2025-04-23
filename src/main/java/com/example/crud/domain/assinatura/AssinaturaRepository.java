package com.example.crud.domain.assinatura;

import com.example.crud.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findTop2ByUsuarioOrderByDataVencimentoAsc(Usuario usuario);
}
