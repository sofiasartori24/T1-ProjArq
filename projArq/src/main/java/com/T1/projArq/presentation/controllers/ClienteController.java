package com.T1.projArq.presentation.controllers;

import com.T1.projArq.aplication.ClienteService;
import com.T1.projArq.aplication.dto.ClienteDTO;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.model.Assinatura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Criar um cliente
    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.createCliente(clienteDTO.getCodigo(), clienteDTO.getNome(), clienteDTO.getEmail());
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getById(@PathVariable("id") Long codigo) {
        Cliente cliente = clienteService.getById(codigo);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCliente(@PathVariable("id") Long codigo, @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente(codigo, clienteDTO.getNome(), clienteDTO.getEmail());
        clienteService.updateCliente(cliente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable("id") Long codigo) {
        clienteService.deleteCliente(codigo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

