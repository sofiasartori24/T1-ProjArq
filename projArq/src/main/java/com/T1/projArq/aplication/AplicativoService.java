package com.T1.projArq.aplication;

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

    public Aplicativo createAplicativo(Long codigo, String nome, Double custoMensal) {
        this.aplicativoRepository.create(codigo, nome, custoMensal);
        return new Aplicativo(codigo, nome, custoMensal);
    }

    public List<Aplicativo> getAllAplicativos() {
        return this.aplicativoRepository.getAll();
    }

}
