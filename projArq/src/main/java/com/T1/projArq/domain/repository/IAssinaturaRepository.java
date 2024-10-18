package com.T1.projArq.domain.repository;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;

import java.util.Date;
import java.util.List;

public interface IAssinaturaRepository {
    Assinatura create(Long codigo, Date inicioVigencia, Aplicativo aplicativo);
    List<Assinatura> getAll();
    Assinatura getById(Long codigo);
    void update(Assinatura assinatura);
    void delete(Long codigo);
}