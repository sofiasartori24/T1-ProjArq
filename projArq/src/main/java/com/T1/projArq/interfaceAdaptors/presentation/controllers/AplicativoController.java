package com.T1.projArq.interfaceAdaptors.presentation.controllers;

import com.T1.projArq.application.dto.AplicativoDTO;
import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.services.AplicativoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/aplicativos")
public class AplicativoController {

    private final AplicativoService aplicativoService;

    @Autowired
    public AplicativoController(AplicativoService aplicativoService) {
        this.aplicativoService = aplicativoService;
    }

    @GetMapping
    public  ResponseEntity<List<AplicativoDTO>> getAllAplicativos() {
        List<AplicativoDTO> aplicativos = aplicativoService.getAllAplicativos();
        return new ResponseEntity<>(aplicativos, HttpStatus.OK);
    }

    // Cria um aplicativo
    @PostMapping("/atualizacusto/{idAplicativo}")
    public ResponseEntity<AplicativoDTO> atualizarCusto(@PathVariable Long idAplicativo, @RequestBody Map<String, Double> request) {
        Double novoCusto = request.get("custo");

        AplicativoDTO aplicativoAtualizado = aplicativoService.updateCusto(idAplicativo, novoCusto);

        return ResponseEntity.ok(aplicativoAtualizado);
    }
}

