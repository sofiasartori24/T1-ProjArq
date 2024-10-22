package com.T1.projArq.domain.services;

import com.T1.projArq.application.dto.AplicativoDTO;
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
    public List<AplicativoDTO> getAllAplicativos() {
        List<Aplicativo> aplicativos = this.aplicativoRepository.getAll();
        return aplicativos.stream().map(this::toDTO).toList();
    }

    // Recupera um aplicativo por id
    public Aplicativo getById(Long codigo) { return this.aplicativoRepository.getById(codigo); }

    // Atualiza o custo mensal de um aplicativo
    public AplicativoDTO updateCusto(Long codigo, Double custoMensal) {
        Aplicativo aplicativo = aplicativoRepository.getById(codigo);
        aplicativo.setCustoMensal(custoMensal);
        this.aplicativoRepository.update(aplicativo);
        return toDTO(aplicativo);
    }


    private AplicativoDTO toDTO(Aplicativo aplicativo) {
        return new AplicativoDTO(aplicativo.getCodigo(), aplicativo.getNome(), aplicativo.getCustoMensal(), aplicativo.getAssinaturas());
    }
}
