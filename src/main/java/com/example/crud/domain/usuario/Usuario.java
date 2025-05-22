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

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;    //Role usuário

    @Column(name = "aceitou_termos")
    private Boolean aceitouTermos;
}

