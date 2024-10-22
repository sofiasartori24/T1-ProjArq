package com.T1.projArq.interfaceAdaptors.infrastructure.dataBase;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.model.Pagamento;
import com.T1.projArq.domain.repository.IPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PagamentoDataBase implements IPagamentoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PagamentoDataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Pagamento create(Long codigo, Double valorPago, Date dataPagamento, String promocao, Long idAssinatura) {
        String sql = "INSERT INTO pagamentos (codigo, valor_pago, data_pagamento, promocao, assinatura_codigo) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, codigo, valorPago, new java.sql.Date(dataPagamento.getTime()), promocao, idAssinatura);

        Assinatura assinatura = getAssinaturaById(idAssinatura);
        return new Pagamento(codigo, valorPago, dataPagamento, promocao, assinatura);
    }


    @Override
    public List<Pagamento> getAll() {
        return new ArrayList<>();
    }

    @Override
    public Pagamento getById(Long codigo) {
        return null;
    }

    @Override
    public void update(Pagamento pagamento) {

    }

    @Override
    public void delete(Long codigo) {

    }
    private Assinatura getAssinaturaById(Long idAssinatura) {
        String sql = "SELECT * FROM assinaturas WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{idAssinatura}, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            Date inicioVigencia = rs.getDate("inicio_vigencia");
            Date fimVigencia = rs.getDate("fim_vigencia");
            Long aplicativoId = rs.getLong("aplicativo_codigo");
            Long clienteId = rs.getLong("cliente_codigo");

            Aplicativo aplicativo = getAplicativoById(aplicativoId);
            Cliente cliente = getClienteById(clienteId);

            return new Assinatura(codigo, inicioVigencia, fimVigencia, aplicativo, cliente);
        });
    }

    private Cliente getClienteById(Long clienteId) {
        String sql = "SELECT * FROM clientes WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{clienteId}, (rs, rowNum) -> {
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            return new Cliente(clienteId, nome, email);
        });
    }

    private Aplicativo getAplicativoById(Long aplicativoId) {
        String sql = "SELECT * FROM aplicativos WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{aplicativoId}, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            String nome = rs.getString("nome");
            Double custoMensal = rs.getDouble("custo_mensal");

            return new Aplicativo(codigo, nome, custoMensal);
        });
    }


}
