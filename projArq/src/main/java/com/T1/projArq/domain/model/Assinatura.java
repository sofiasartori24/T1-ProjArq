package com.T1.projArq.domain.model;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Assinatura {
    private Long codigo;
    private Date inicioVigencia;
    private Date fimVigencia;
    private List<Pagamento> pagamentos;
    private Aplicativo aplicativo;
    private Cliente cliente;

    public Assinatura(Long codigo, Date inicioVigencia, Date fimVigencia, Aplicativo aplicativo, Cliente cliente) {
        this.codigo = codigo;
        this.inicioVigencia = inicioVigencia;
        this.pagamentos = new ArrayList<>();
        this.fimVigencia = fimVigencia;
        this.aplicativo = aplicativo;
        this.cliente = cliente;
    }

    public Long getCodigo() {
        return codigo;
    }

    public Date getFimVigencia() {
        return fimVigencia;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public Aplicativo getAplicativo() { return aplicativo; }

    public Cliente getCliente() { return cliente; }
    public void setFimVigencia(Date fimVigencia) {
        this.fimVigencia = fimVigencia;
    }

    public void adicionarPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<Pagamento> pagamentosByAssinaturaId) {
        this.pagamentos = pagamentosByAssinaturaId;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
}
