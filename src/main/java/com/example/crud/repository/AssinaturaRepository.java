package com.example.crud.repository;

import com.example.crud.domain.Assinatura;
import com.example.crud.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {

    List<Assinatura> findTop3ByUsuarioOrderByDataVencimentoAsc(Usuario usuario);

    List<Assinatura> findByUsuario(Usuario usuario);

    Page<Assinatura> findByUsuario(Usuario usuario, Pageable pageable);

    List<Assinatura> findByDataVencimento(LocalDate data);

    List<Assinatura> findByUsuarioAndStatusIgnoreCase(Usuario usuario, String status);

}
