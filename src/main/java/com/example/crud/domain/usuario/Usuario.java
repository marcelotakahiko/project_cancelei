package com.example.crud.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String email;
    private String senha;
    private Boolean active = true;

    @Column(name = "aceitou_termos")
    private Boolean aceitouTermos;
}

//CREATE EXTENSION IF NOT EXISTS "pgcrypto"