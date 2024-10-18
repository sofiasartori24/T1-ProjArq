package com.T1.projArq.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private Long codigo;
    private String nome;
    private String email;
    private List<Assinatura> assinaturas;

    public Cliente(Long codigo, String nome, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.assinaturas = new ArrayList<>();
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public List<Assinatura> getAssinaturas() {
        return assinaturas;
    }

    public void addAssinatura(Assinatura assinatura) {
        this.assinaturas.add(assinatura);
    }

    public void removeAssinatura(Assinatura assinatura) {
        this.assinaturas.remove(assinatura);
    }
}
