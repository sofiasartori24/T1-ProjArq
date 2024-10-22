package com.T1.projArq.application.dto;

public class PagamentoRequestDTO {
    private int dia;
    private int mes;
    private int ano;
    private Long codass;
    private Double valorPago;
    private String promocao;

    public PagamentoRequestDTO(int dia, int mes, int ano, Long codass, Double valorPago1, String promocao1) {
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;

        this.codass = codass;
        this.valorPago = valorPago1;
        this.promocao = promocao1;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public Long getCodass() {
        return codass;
    }

    public String getPromocao() {
        return promocao;
    }
}