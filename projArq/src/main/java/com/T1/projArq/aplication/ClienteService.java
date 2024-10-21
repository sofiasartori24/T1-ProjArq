package com.T1.projArq.aplication;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.repository.IAplicativoRepository;
import com.T1.projArq.domain.repository.IAssinaturaRepository;
import com.T1.projArq.domain.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.util.Date;
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
    public List<Cliente> getAllClientes() {
        return clienteRepository.getAll();
    }

    // Recupera um cliente por id
    public Cliente getById(Long codigo) {
        return clienteRepository.getById(codigo);
    }

    // Atualiza o cliente
    public void updateCliente(Cliente cliente) {
        clienteRepository.update(cliente);
    }

    // Deleta um cliente
    public void deleteCliente(Long codigo) {
        clienteRepository.delete(codigo);
    }

}

