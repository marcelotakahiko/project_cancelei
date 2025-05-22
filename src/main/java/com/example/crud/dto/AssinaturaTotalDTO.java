package com.example.crud.dto;

import java.math.BigDecimal;

public class AssinaturaTotalDTO {
    private String nome;
    private BigDecimal total;

    public AssinaturaTotalDTO(String nome, BigDecimal total) {
        this.nome = nome;
        this.total = total;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
