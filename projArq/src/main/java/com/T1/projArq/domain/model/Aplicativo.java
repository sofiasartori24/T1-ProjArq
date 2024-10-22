package com.T1.projArq.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Aplicativo {
    private Long codigo;
    private String nome;
    private Double custoMensal;
    private List<Assinatura> assinaturas;

    public Aplicativo(Long codigo, String nome, Double custoMensal) {
        this.codigo = codigo;
        this.nome = nome;
        this.custoMensal = custoMensal;
        this.assinaturas = new ArrayList<Assinatura>();
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public Double getCustoMensal() {
        return this.custoMensal;
    }

    public List<Assinatura> getAssinaturas() {
        return this.assinaturas;
    }

    public void setCustoMensal(Double custoMensal) {
        this.custoMensal = custoMensal;
    }
}
