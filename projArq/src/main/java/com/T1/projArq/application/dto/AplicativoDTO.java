package com.T1.projArq.application.dto;

import com.T1.projArq.domain.model.Assinatura;

import java.util.List;

public class AplicativoDTO {
    private Long codigo;
    private String nome;
    private Double custoMensal;
    private List<Assinatura> assinaturas;

    public Long getCodigo() {
        return this.codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public Double getCustoMensal() {
        return this.custoMensal;
    }
}
