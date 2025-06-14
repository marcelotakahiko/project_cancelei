package com.example.crud.domain;

import com.example.crud.security.Role;
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

    @Transient
    private String confirmarSenha;                                                              //não persiste no banco

    private Boolean active = true;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(name = "aceitou_termos")
    private Boolean aceitouTermos;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}