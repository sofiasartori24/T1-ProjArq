package com.T1.projArq.domain.model;

import java.util.Date;

public class Pagamento {
    private Long codigo;
    private Double valorPago;
    private Date dataPagamento;
    private String promocao;
    private Assinatura assinatura;

    public Pagamento(Long codigo, Double valorPago, Date dataPagamento, String promocao, Assinatura assinatura) {
        this.codigo = codigo;
        this.valorPago = valorPago;
        this.dataPagamento  = dataPagamento;
        this.promocao = promocao;
        this.assinatura = assinatura;
    }

    public Long getCodigo() {
        return codigo;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public String getPromocao() {
        return promocao;
    }

    public Double getValorPago() {
        return valorPago;
    }

     public Assinatura getAssinatura() {
        return assinatura; 
    }
}
