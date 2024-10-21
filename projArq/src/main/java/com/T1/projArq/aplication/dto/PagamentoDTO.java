package com.T1.projArq.application.dto;

import java.util.Date;

public class PagamentoDTO {
    private Long assinaturaId;
    private Double valorPago;
    private Date dataPagamento;
    private String promocao;

    public Long getAssinaturaId() {
        return assinaturaId;
    }

    public Double getValorPago() {
        return valorPago;
    }


    public Date getDataPagamento() {
        return dataPagamento;
    }

    public String getPromocao() {
        return promocao;
    }
}