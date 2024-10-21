package com.T1.projArq.presentation.controllers;

import com.T1.projArq.aplication.AssinaturaService;
import com.T1.projArq.aplication.ClienteService;
import com.T1.projArq.domain.model.Assinatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @Autowired
    public AssinaturaController(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    // Cria uma assinatura
    @PostMapping
    public ResponseEntity<Assinatura> createAssinatura(@RequestBody Map<String, Long> request) {
        Long codCliente = request.get("codigoCliente");
        Long codAplicativo = request.get("codigoAplicativo");

        Assinatura assinatura = assinaturaService.createAssinatura(codCliente, codAplicativo);

        return ResponseEntity.ok(assinatura);
    }

    // Obtém todas as assinaturas
    @GetMapping
    public ResponseEntity<List<Assinatura>> getAssinaturas() {
        List<Assinatura> assinaturas = assinaturaService.getAll();
        if (assinaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(assinaturas);
    }

    // Obtém assinaturas por tipo (ATIVA/INATIVA)
    @GetMapping("/{tipo}")
    public ResponseEntity<List<Assinatura>> getAssinaturasbyType(@PathVariable String tipo) {
        List<Assinatura> assinaturas = assinaturaService.getAssinaturasByType(tipo.toUpperCase());

        if (assinaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(assinaturas);
    }
}
