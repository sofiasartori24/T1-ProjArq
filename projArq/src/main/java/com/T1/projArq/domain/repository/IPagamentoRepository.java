package com.T1.projArq.domain.repository;

import com.T1.projArq.domain.model.Pagamento;

import java.util.Date;
import java.util.List;

public interface IPagamentoRepository {
    Pagamento create(Long codigo, Double valorPago, Date dataPagamento, String promocao, Long idAssinatura);
    List<Pagamento> getAll();
    Pagamento getById(Long codigo);
    void update(Pagamento pagamento);
    void delete(Long codigo);
}