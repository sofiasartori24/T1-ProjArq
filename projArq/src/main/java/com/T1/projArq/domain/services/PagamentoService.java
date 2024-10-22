package com.T1.projArq.domain.services;

import com.T1.projArq.application.dto.PagamentoResponseDTO;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Pagamento;
import com.T1.projArq.domain.repository.IAssinaturaRepository;
import com.T1.projArq.domain.repository.IPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PagamentoService {

    private final IPagamentoRepository pagamentoRepository;
    private final IAssinaturaRepository assinaturaRepository;

    @Autowired
    public PagamentoService(IPagamentoRepository pagamentoRepository, IAssinaturaRepository assinaturaRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.assinaturaRepository = assinaturaRepository;
    }

    // Cria um pagamento e ajusta a validade da assinatura conforme as regras de negócio
    public PagamentoResponseDTO create(Long assinaturaId, Double valorPago, Date dataPagamento, String promocao) throws IllegalArgumentException {
        // Busca a assinatura pelo id
        Assinatura assinatura = assinaturaRepository.getById(assinaturaId);
        if (assinatura == null) {
            throw new IllegalArgumentException("Assinatura não encontrada");
        }

        // Verifica o custo mensal do aplicativo
        Double custoMensal = assinatura.getAplicativo().getCustoMensal();
        
        // Tratamento de valor incorreto: pagamento inferior ao custo mensal
        if (valorPago < custoMensal) {
            return new PagamentoResponseDTO("VALOR_INCORRETO", assinatura.getFimVigencia(), valorPago);
        }

        // Aplica o valor excedente (se houver)
        Double valorExcedente = valorPago - custoMensal;

        Calendar calendar = Calendar.getInstance();
        // Ajusta a data de validade da assinatura
        if (dataPagamento.before(assinatura.getFimVigencia())) {
            // Caso o pagamento seja dentro da validade da assinatura
            calendar.setTime(assinatura.getFimVigencia());
        } else {
            // Caso a assinatura já esteja expirada
            calendar.setTime(dataPagamento);
        }

        // Verifica a promoção aplicada
        if ("anual40".equals(promocao)) {
            // Promoção: pagamento anual com desconto de 40%
            calendar.add(Calendar.YEAR, 1);
        } else if ("30pague45ganhe".equals(promocao)) {
            // Promoção: pague 30 e ganhe 45 dias
            calendar.add(Calendar.DAY_OF_MONTH, 45);
        } else {
            // Plano básico: pagamento por 30 dias
            calendar.add(Calendar.DAY_OF_MONTH, 30);
        }

        // Atualiza a validade da assinatura
        assinatura.setFimVigencia(calendar.getTime());
        assinaturaRepository.update(assinatura);

        PagamentoResponseDTO pagamentoResponseDTO = new PagamentoResponseDTO("PAGAMENTO_OK", calendar.getTime(), valorExcedente);

        // Cria o pagamento e armazena no repositório
        Pagamento pagamento = new Pagamento(assinaturaId, valorPago, dataPagamento, promocao, assinatura);
        pagamentoRepository.create(valorPago, dataPagamento, promocao, assinatura.getCodigo());

        return pagamentoResponseDTO;
    }
}