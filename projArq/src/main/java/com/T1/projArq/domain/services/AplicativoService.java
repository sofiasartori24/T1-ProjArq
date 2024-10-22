package com.T1.projArq.domain.services;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.repository.IAplicativoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AplicativoService {
    private final IAplicativoRepository aplicativoRepository;

    public AplicativoService(IAplicativoRepository aplicativoRepository) {
        this.aplicativoRepository = aplicativoRepository;
    }

    // Cria um aplicativo
    public Aplicativo createAplicativo(Long codigo, String nome, Double custoMensal) {
        this.aplicativoRepository.create(codigo, nome, custoMensal);
        return new Aplicativo(codigo, nome, custoMensal);
    }

    // Recupera todos os aplicativos
    public List<Aplicativo> getAllAplicativos() {
        return this.aplicativoRepository.getAll();
    }

    // Recupera um aplicativo por id
    public Aplicativo getById(Long codigo) { return this.aplicativoRepository.getById(codigo); }

    // Atualiza o custo mensal de um aplicativo
    public Aplicativo updateCusto(Long codigo, Double custoMensal) {
        Aplicativo aplicativo = aplicativoRepository.getById(codigo);
        aplicativo.setCustoMensal(custoMensal);
        this.aplicativoRepository.update(aplicativo);
        return aplicativo;
    }

}
