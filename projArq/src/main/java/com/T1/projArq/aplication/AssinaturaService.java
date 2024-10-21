package com.T1.projArq.aplication;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.repository.IAssinaturaRepository;
import com.T1.projArq.infrastructure.dataBase.AssinaturaDataBase;
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
    public Assinatura createAssinatura(Long codCliente, Long codAplicativo) {
        Cliente cliente = clienteService.getById(codCliente);
        Aplicativo aplicativo = aplicativoService.getById(codAplicativo);

        Date inicioVigencia = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inicioVigencia);
        calendar.add(Calendar.DAY_OF_MONTH, 7); // Adiciona 7 dias grátis
        Date fimVigencia = calendar.getTime();

        Assinatura assinatura = new Assinatura(null, inicioVigencia, fimVigencia, aplicativo, cliente);
        assinaturaDataBase.create(assinatura, cliente);

        return assinatura;
    }

    public List<Assinatura> getAll() {
        return this.assinaturaDataBase.getAll();
    }

    // Recupera assinaturas por tipo (ATIVA/INATIVA)
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
}