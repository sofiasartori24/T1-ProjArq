package com.T1.projArq.presentation.controllers;

import com.T1.projArq.aplication.PagamentoService;
import com.T1.projArq.application.dto.PagamentoDTO;
import com.T1.projArq.domain.model.Pagamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // Cria um pagamento
    @PostMapping
    public ResponseEntity<PagamentoDTO> createPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        try {
            Pagamento pagamento = pagamentoService.create(
                    pagamentoDTO.getAssinaturaId(),
                    pagamentoDTO.getValorPago(),
                    pagamentoDTO.getDataPagamento(),
                    pagamentoDTO.getPromocao()
            );
            return ResponseEntity.ok(pagamentoDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Obtém todos os pagamentos
    @GetMapping
    public ResponseEntity<List<Pagamento>> getAllPagamentos() {
        List<Pagamento> pagamentos = pagamentoService.getAll();
        if (pagamentos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagamentos);
    }

    // Obtém um pagamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable Long id) {
        Pagamento pagamento = pagamentoService.getById(id);
        if (pagamento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pagamento);
    }
}