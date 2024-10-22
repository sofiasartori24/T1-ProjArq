package com.T1.projArq.application.dto;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.model.Pagamento;

import java.util.Date;
import java.util.List;

public class AssinaturaDTO {
    private Long codigo;
    private Date inicioVigencia;
    private Date fimVigencia;
    private List<Pagamento> pagamentos;
    private Aplicativo aplicativo;
    private Cliente cliente;

    private String status;

    public AssinaturaDTO(Long codigo, Date inicioVigencia, Date fimVigencia, List<Pagamento> pagamentos, Aplicativo aplicativo, Cliente cliente, String status) {
        this.codigo = codigo;
        this.inicioVigencia = inicioVigencia;
        this.fimVigencia = fimVigencia;
        this.pagamentos = pagamentos;
        this.aplicativo = aplicativo;
        this.cliente = cliente;
        this.status = status;
    }
    public Long getCodigo() {
        return codigo;
    }

    public Date getInicioVigencia() {
        return inicioVigencia;
    }

    public Date getFimVigencia() {
        return fimVigencia;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public Aplicativo getAplicativo() {
        return aplicativo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getStatus() {
        return status;
    }
}
