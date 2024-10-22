package com.T1.projArq.interfaceAdaptors.presentation.controllers;

import com.T1.projArq.application.dto.PagamentoRequestDTO;
import com.T1.projArq.application.dto.PagamentoResponseDTO;
import com.T1.projArq.domain.model.Pagamento;
import com.T1.projArq.domain.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Cria um pagamento
    @PostMapping("/registrarpagamento")
    public ResponseEntity<PagamentoResponseDTO> createPagamento(@RequestBody PagamentoRequestDTO pagamentoRequestDTO) {
        try {
            // Monta a data a partir de dia, mês e ano
            Calendar calendar = Calendar.getInstance();
            calendar.set(pagamentoRequestDTO.getAno(), pagamentoRequestDTO.getMes() - 1, pagamentoRequestDTO.getDia()); // Mês começa em 0 em Calendar

            Date dataPagamento = calendar.getTime();

            // Chama o serviço de criação do pagamento
            PagamentoResponseDTO responseDTO = pagamentoService.create(
                    pagamentoRequestDTO.getCodass(),
                    pagamentoRequestDTO.getValorPago(),
                    dataPagamento,
                    pagamentoRequestDTO.getPromocao()
            );

            return ResponseEntity.ok(responseDTO);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}