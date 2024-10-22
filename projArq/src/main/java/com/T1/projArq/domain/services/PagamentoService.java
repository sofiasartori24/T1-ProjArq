package com.T1.projArq.domain.services;

import com.T1.projArq.application.dto.PagamentoDTO;
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
    public Pagamento create(Long assinaturaId, Double valorPago, Date dataPagamento, String promocao) throws IllegalArgumentException {
        // Busca a assinatura pelo id
        Assinatura assinatura = assinaturaRepository.getById(assinaturaId);
        if (assinatura == null) {
            throw new IllegalArgumentException("Assinatura não encontrada");
        }

        // Verifica o custo mensal do aplicativo
        Double custoMensal = assinatura.getAplicativo().getCustoMensal();
        
        // Tratamento de valor incorreto: pagamento inferior ao custo mensal
        if (valorPago < custoMensal) {
            throw new IllegalArgumentException("Valor pago é inferior ao custo mensal.");
        }

        // Aplica o valor excedente (se houver)
        Double valorExcedente = valorPago - custoMensal;
        if (valorExcedente > 0) {
            // Se o valor for maior, podemos indicar o valor a ser estornado
            return new Pagamento(assinaturaId, valorPago, dataPagamento, promocao, assinatura);
        }

        // Verifica a promoção aplicada
        Calendar calendar = Calendar.getInstance();
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

        // Ajusta a data de validade da assinatura
        if (dataPagamento.before(assinatura.getFimVigencia())) {
            // Caso o pagamento seja dentro da validade da assinatura
            calendar.setTime(assinatura.getFimVigencia());
        } else {
            // Caso a assinatura já esteja expirada
            calendar.setTime(dataPagamento);
        }

        // Atualiza a validade da assinatura
        assinatura.setFimVigencia(calendar.getTime());
        assinaturaRepository.update(assinatura);

        // Cria o pagamento e armazena no repositório
        Pagamento pagamento = new Pagamento(assinaturaId, valorPago, dataPagamento, promocao, assinatura);
        pagamentoRepository.create(assinaturaId, valorPago, dataPagamento, promocao, assinatura.getCodigo());

        return pagamento;
    }

    // Método para obter todos os pagamentos
    public List<PagamentoDTO> getAll() {
        List<Pagamento> pagamentos = pagamentoRepository.getAll();

        // Converte a lista de pagamentos para uma lista de DTOs
        return pagamentos.stream().map(this::toDTO).toList();

    }

    // Método para obter um pagamento por id
    public PagamentoDTO getById(Long codigo) {
        Pagamento pagamento = pagamentoRepository.getById(codigo);
        if (pagamento == null) {
            return null;
        }
        return toDTO(pagamento);
    }

    private PagamentoDTO toDTO(Pagamento pagamento) {
        return new PagamentoDTO(pagamento.getCodigo(), pagamento.getValorPago(), pagamento.getDataPagamento(), pagamento.getPromocao());
    }
}