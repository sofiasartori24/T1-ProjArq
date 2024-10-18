package com.T1.projArq.presentation.controllers;

import com.T1.projArq.aplication.AplicativoService;
import com.T1.projArq.aplication.ClienteService;
import com.T1.projArq.aplication.dto.AplicativoDTO;
import com.T1.projArq.aplication.dto.ClienteDTO;
import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Cliente;
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
    public  ResponseEntity<List<Aplicativo>> getAllAplicativos() {
        List<Aplicativo> aplicativos = aplicativoService.getAllAplicativos();
        return new ResponseEntity<>(aplicativos, HttpStatus.OK);
    }
    @PostMapping("/atualizacusto/{idAplicativo}")
    public ResponseEntity<Aplicativo> atualizarCusto(@PathVariable Long idAplicativo, @RequestBody Map<String, Double> request) {
        Double novoCusto = request.get("custo");

        Aplicativo aplicativoAtualizado = aplicativoService.updateCusto(idAplicativo, novoCusto);

        return ResponseEntity.ok(aplicativoAtualizado);
    }
}

