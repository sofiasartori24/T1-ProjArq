package com.T1.projArq.domain.repository;

import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Cliente;

import java.util.List;

public interface IAssinaturaRepository {
    Assinatura create(Assinatura assinatura, Cliente cliente);
    List<Assinatura> getAll();
    Assinatura getById(Long codigo);
    List<Assinatura> getByAplicativoId(Long aplicativoId);
    void update(Assinatura assinatura);
    void delete(Long codigo);
}