package com.T1.projArq.interfaceAdaptors.presentation.controllers;

import com.T1.projArq.aplication.dto.AssinaturaDTO;
import com.T1.projArq.domain.services.AssinaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/servcad")
public class AssinaturaController {

    private final AssinaturaService assinaturaService;

    @Autowired
    public AssinaturaController(AssinaturaService assinaturaService) {
        this.assinaturaService = assinaturaService;
    }

    // Cria uma assinatura
    @PostMapping
    public ResponseEntity<AssinaturaDTO> createAssinatura(@RequestBody Map<String, Long> request) {
        Long codCliente = request.get("codigoCliente");
        Long codAplicativo = request.get("codigoAplicativo");

        AssinaturaDTO assinatura = assinaturaService.createAssinatura(codCliente, codAplicativo);

        return ResponseEntity.ok(assinatura);
    }

    // Obtém todas as assinaturas
    @GetMapping
    public ResponseEntity<List<AssinaturaDTO>> getAssinaturas() {
        List<AssinaturaDTO> assinaturas = assinaturaService.getAll();
        if (assinaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(assinaturas);
    }

    // Obtém assinaturas por tipo (ATIVA/INATIVA)
    @GetMapping("/assinaturas/{tipo}")
    public ResponseEntity<List<AssinaturaDTO>> getAssinaturasbyType(@PathVariable String tipo) {
        List<AssinaturaDTO> assinaturas = assinaturaService.getAssinaturasByType(tipo.toUpperCase());

        if (assinaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(assinaturas);
    }

    @GetMapping("/asscli/{codcli}")
    public ResponseEntity<List<AssinaturaDTO>> getAssinaturasByCliente(@PathVariable Long codcli) {
        List<AssinaturaDTO> assinaturas = assinaturaService.getAssinaturasByCliente(codcli);

        if (assinaturas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(assinaturas);
    }

    @GetMapping("/assinvalida/{codass}")
    public Boolean isAssinaturaAtiva(@PathVariable Long codass) {
        return assinaturaService.isAssinaturaAtiva(codass);
    }
}
