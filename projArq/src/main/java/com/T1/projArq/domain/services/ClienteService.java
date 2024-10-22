package com.T1.projArq.domain.services;

import com.T1.projArq.application.dto.ClienteDTO;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.repository.IAplicativoRepository;
import com.T1.projArq.domain.repository.IAssinaturaRepository;
import com.T1.projArq.domain.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final IClienteRepository clienteRepository;
    private final IAssinaturaRepository assinaturaRepository;
    private final IAplicativoRepository aplicativoRepository;

    @Autowired
    public ClienteService(IClienteRepository clienteRepository, IAssinaturaRepository assinaturaRepository, IAplicativoRepository aplicativoRepository) {
        this.clienteRepository = clienteRepository;
        this.assinaturaRepository = assinaturaRepository;
        this.aplicativoRepository = aplicativoRepository;
    }

    // Cria um cliente
    public Cliente createCliente(Long codigo, String nome, String email) {
        return clienteRepository.create(codigo, nome, email);
    }

    // Recupera todos os clientes
    public List<ClienteDTO> getAllClientes() {
        List<Cliente> clientes = clienteRepository.getAll();
        return clientes.stream().map(this::toDTO).toList();
    }

    // Recupera um cliente por id
    public Cliente getById(Long codigo) {
        return clienteRepository.getById(codigo);
    }

    private ClienteDTO toDTO(Cliente cliente) {
        return new ClienteDTO(cliente.getCodigo(), cliente.getNome(), cliente.getEmail(), cliente.getAssinaturas());
    }
}

