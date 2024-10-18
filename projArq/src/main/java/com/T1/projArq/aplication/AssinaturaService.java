package com.T1.projArq.aplication;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.repository.IAssinaturaRepository;
import com.T1.projArq.infrastructure.dataBase.AssinaturaDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssinaturaService {

    private final AssinaturaDataBase assinaturaDataBase;
    private final ClienteService clienteService;
    private final AplicativoService aplicativoService;

    @Autowired
    public AssinaturaService(AssinaturaDataBase assinaturaDataBase, ClienteService clienteService, AplicativoService aplicativoService) {
        this.assinaturaDataBase = assinaturaDataBase;
        this.clienteService = clienteService;
        this.aplicativoService = aplicativoService;
    }

    public Assinatura createAssinatura(Long codCliente, Long codAplicativo) {
        Cliente cliente = clienteService.getById(codCliente);
        Aplicativo aplicativo = aplicativoService.getById(codAplicativo);

        Date inicioVigencia = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inicioVigencia);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date fimVigencia = calendar.getTime();

        Assinatura assinatura = new Assinatura(null, inicioVigencia, fimVigencia, aplicativo, cliente);
        assinaturaDataBase.create(assinatura, cliente);

        return assinatura;
    }
    public List<Assinatura> getAll() {
        return this.assinaturaDataBase.getAll();
    }

    public List<Assinatura> getAssinaturasByType(String type) {
        List<Assinatura> assinaturas = assinaturaDataBase.getAll();
        Date today = new Date();

        if (type.equals("TODAS")) {
            return assinaturas;
        }

        return assinaturas.stream()
                .filter(assinatura -> {
                    if ("ATIVA".equals(type)) {
                        return assinatura.getFimVigencia().after(today);
                    } else if ("INATIVA".equals(type)) {
                        return assinatura.getFimVigencia().before(today);
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }
}

