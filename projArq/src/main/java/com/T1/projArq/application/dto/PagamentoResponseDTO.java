package com.T1.projArq.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PagamentoResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date dataPagamento;
    private Double valorEstornado;
    private String status;

    public PagamentoResponseDTO( String status, Date dataPagamento, Double valorEstornado) {
        this.dataPagamento = dataPagamento;
        this.valorEstornado = valorEstornado;
        this.status = status;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public Double getValorEstornado() {
        return valorEstornado;
    }

    public String getStatus() {
        return status;
    }

}
