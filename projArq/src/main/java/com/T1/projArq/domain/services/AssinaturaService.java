package com.T1.projArq.domain.services;

import com.T1.projArq.application.dto.AssinaturaDTO;
import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.interfaceAdaptors.infrastructure.dataBase.AssinaturaDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Criação da assinatura com 7 dias grátis
    public AssinaturaDTO createAssinatura(Long codCliente, Long codAplicativo) {
        Cliente cliente = clienteService.getById(codCliente);
        Aplicativo aplicativo = aplicativoService.getById(codAplicativo);

        Date inicioVigencia = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inicioVigencia);
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Adiciona 7 dias grátis
        Date fimVigencia = calendar.getTime();

        Assinatura assinatura = new Assinatura(null, inicioVigencia, fimVigencia, aplicativo, cliente);
        assinaturaDataBase.create(assinatura, cliente);

        AssinaturaDTO assinaturaDTO = new AssinaturaDTO(
                assinatura.getCodigo(),
                assinatura.getInicioVigencia(),
                assinatura.getFimVigencia(),
                assinatura.getPagamentos(),
                assinatura.getAplicativo(),
                assinatura.getCliente(),
                (assinatura.getFimVigencia() != null && new Date().before(assinatura.getFimVigencia())) ? "ATIVA" : "CANCELADA" // Status
        );
        return assinaturaDTO;
    }

    public List<AssinaturaDTO> getAll() {
        List<Assinatura> assinaturas = assinaturaDataBase.getAll();

        return assinaturas.stream()
                .map(assinatura -> new AssinaturaDTO(
                        assinatura.getCodigo(),
                        assinatura.getInicioVigencia(),
                        assinatura.getFimVigencia(),
                        assinatura.getPagamentos(),
                        assinatura.getAplicativo(),
                        assinatura.getCliente(),
                        (assinatura.getFimVigencia() != null && new Date().before(assinatura.getFimVigencia())) ? "ATIVA" : "CANCELADA" // Status
                ))
                .collect(Collectors.toList());
    }

    // Recupera assinaturas por tipo (ATIVA/INATIVA)
    public List<AssinaturaDTO> getAssinaturasByType(String type) {
        List<Assinatura> assinaturas = assinaturaDataBase.getAll();
        Date today = new Date();

        if ("TODAS".equals(type)) {
            return assinaturas.stream()
                    .map(assinatura -> new AssinaturaDTO(
                            assinatura.getCodigo(),
                            assinatura.getInicioVigencia(),
                            assinatura.getFimVigencia(),
                            assinatura.getPagamentos(),
                            assinatura.getAplicativo(),
                            assinatura.getCliente(),
                            (assinatura.getFimVigencia() != null && today.before(assinatura.getFimVigencia())) ? "ATIVA" : "CANCELADA" // Status
                    ))
                    .collect(Collectors.toList());
        }

        return assinaturas.stream()
                .filter(assinatura -> {
                    if ("ATIVA".equals(type)) {
                        return assinatura.getFimVigencia() != null && assinatura.getFimVigencia().after(today);
                    } else if ("CANCELADA".equals(type)) {
                        return assinatura.getFimVigencia() != null && assinatura.getFimVigencia().before(today);
                    }
                    return false;
                })
                .map(assinatura -> new AssinaturaDTO(
                        assinatura.getCodigo(),
                        assinatura.getInicioVigencia(),
                        assinatura.getFimVigencia(),
                        assinatura.getPagamentos(),
                        assinatura.getAplicativo(),
                        assinatura.getCliente(),
                        (assinatura.getFimVigencia() != null && today.before(assinatura.getFimVigencia())) ? "ATIVA" : "CANCELADA" // Status
                ))
                .collect(Collectors.toList());
    }


    // Atualiza a vigência da assinatura após pagamento de mensalidade
    public Assinatura atualizarAssinaturaPorPagamento(Long codAssinatura, Date dataPagamento, String promocao) {
        Assinatura assinatura = assinaturaDataBase.getById(codAssinatura);
        if (assinatura == null) {
            throw new IllegalArgumentException("Assinatura não encontrada");
        }

        Calendar calendar = Calendar.getInstance();
        if (dataPagamento.before(assinatura.getFimVigencia())) {
            // Assinatura ainda ativa
            calendar.setTime(assinatura.getFimVigencia());
        } else {
            // Assinatura cancelada ou expirada
            calendar.setTime(dataPagamento);
        }

        // Tratamento de promoções
        if ("anual40".equals(promocao)) {
            // Promoção de 40% de desconto para pagamento anual
            calendar.add(Calendar.YEAR, 1); // Adiciona 1 ano à vigência
        } else if ("30pague45ganhe".equals(promocao)) {
            // Promoção de pague 30 e ganhe 45 dias
            calendar.add(Calendar.DAY_OF_MONTH, 45); // Adiciona 45 dias
        } else {
            // Plano básico: adiciona 30 dias
            calendar.add(Calendar.DAY_OF_MONTH, 30); // Adiciona 30 dias
        }

        // Atualiza a data de fim de vigência da assinatura
        assinatura.setFimVigencia(calendar.getTime());
        assinaturaDataBase.update(assinatura);

        return assinatura;
    }

    // Reativação de assinaturas canceladas (a partir do pagamento)
    public Assinatura reativarAssinatura(Long codAssinatura, Double valorPago, Date dataPagamento) {
        Assinatura assinatura = assinaturaDataBase.getById(codAssinatura);
        if (assinatura == null) {
            throw new IllegalArgumentException("Assinatura não encontrada");
        }

        Aplicativo aplicativo = assinatura.getAplicativo();
        if (valorPago < aplicativo.getCustoMensal()) {
            throw new IllegalArgumentException("Valor pago é inferior ao custo mensal.");
        }

        // Se a assinatura estiver cancelada, reativa somando 30 dias a partir da data de pagamento
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dataPagamento);
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Adiciona 30 dias

        assinatura.setFimVigencia(calendar.getTime());
        assinaturaDataBase.update(assinatura);

        return assinatura;
    }

    public List<AssinaturaDTO> getAssinaturasByCliente(Long codcli) {
        Cliente cliente = clienteService.getById(codcli);
        List<Assinatura> assinaturas = assinaturaDataBase.getAll();

        return assinaturas.stream()
                .filter(assinatura -> assinatura.getCliente().getCodigo().equals(codcli)) // Filtra pelas assinaturas do cliente
                .map(assinatura -> new AssinaturaDTO(
                        assinatura.getCodigo(),
                        assinatura.getInicioVigencia(),
                        assinatura.getFimVigencia(),
                        assinatura.getPagamentos(),
                        assinatura.getAplicativo(),
                        assinatura.getCliente(),
                        (assinatura.getFimVigencia() != null && new Date().before(assinatura.getFimVigencia())) ? "ATIVA" : "CANCELADA" // Status
                ))
                .collect(Collectors.toList());
    }

    public List<AssinaturaDTO> getAssinaturasByAplicativo(Long codapp) {
        List<Assinatura> assinaturas = assinaturaDataBase.getByAplicativoId(codapp);

        return assinaturas.stream()
                .map(assinatura -> new AssinaturaDTO(
                        assinatura.getCodigo(),
                        assinatura.getInicioVigencia(),
                        assinatura.getFimVigencia(),
                        assinatura.getPagamentos(),
                        assinatura.getAplicativo(),
                        assinatura.getCliente(),
                        (assinatura.getFimVigencia() != null && new Date().before(assinatura.getFimVigencia())) ? "ATIVA" : "CANCELADA" // Status
                ))
                .collect(Collectors.toList());
    }


    public Boolean isAssinaturaAtiva(Long codass) {
        Assinatura assinatura = assinaturaDataBase.getById(codass);

        return assinatura.getFimVigencia() != null && new Date().before(assinatura.getFimVigencia()) ? true : false;
    }
}