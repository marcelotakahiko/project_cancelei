package com.example.crud.domain.assinatura;

import com.example.crud.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findTop3ByUsuarioOrderByDataVencimentoAsc(Usuario usuario);

    List<Assinatura> findByUsuario(Usuario usuario);

    Page<Assinatura> findByUsuario(Usuario usuario, Pageable pageable);
}
