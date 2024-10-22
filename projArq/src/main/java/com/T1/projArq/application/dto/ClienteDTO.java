package com.T1.projArq.application.dto;

import com.T1.projArq.domain.model.Assinatura;

import java.util.List;

public class ClienteDTO {
    private Long codigo;
    private String nome;
    private String email;
    private List<Assinatura> assinaturas;

    public ClienteDTO(Long codigo, String nome, String email, List<Assinatura> assinaturas) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.assinaturas = assinaturas.size() > 0 ? assinaturas : null;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

}

