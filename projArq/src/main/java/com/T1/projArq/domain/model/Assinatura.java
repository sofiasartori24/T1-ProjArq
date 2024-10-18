package com.T1.projArq.domain.model;

import java.util.*;

public class Assinatura {
    private Long codigo;
    private Date inicioVigencia;
    private Date fimVigencia;
    private List<Pagamento> pagamentos;
    private Aplicativo aplicativo;

    public Assinatura(Long codigo, Date inicioVigencia, Aplicativo aplicativo) {
        this.codigo = codigo;
        this.inicioVigencia = inicioVigencia;
        this.pagamentos = new ArrayList<>();
        this.aplicativo = aplicativo;
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
}
