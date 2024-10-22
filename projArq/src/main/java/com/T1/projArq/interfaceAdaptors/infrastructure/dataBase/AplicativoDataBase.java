package com.T1.projArq.interfaceAdaptors.infrastructure.dataBase;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.repository.IAplicativoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AplicativoDataBase implements IAplicativoRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AplicativoDataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Aplicativo create(Long codigo, String nome, Double custoMensal) {
        String sql = "INSERT INTO aplicativos (codigo, nome, custo_mensal) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, codigo, nome, custoMensal);
        return new Aplicativo(codigo, nome, custoMensal);
    }

    @Override
    public List<Aplicativo> getAll() {
        String sql = "SELECT * FROM aplicativos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            String nome = rs.getString("nome");
            Double custoMensal = rs.getDouble("custo_mensal");

            return new Aplicativo(codigo, nome, custoMensal);
        });
    }

    @Override
    public Aplicativo getById(Long codigo) {
        String sql = "SELECT * FROM aplicativos WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{codigo}, (rs, rowNum) -> {
            String nome = rs.getString("nome");
            Double custoMensal = rs.getDouble("custo_mensal");
            return new Aplicativo(codigo, nome, custoMensal);
        });
    }

    @Override
    public void update(Aplicativo aplicativo) {
        String sql = "UPDATE aplicativos SET nome = ?, custo_mensal = ? WHERE codigo = ?";
        jdbcTemplate.update(sql, aplicativo.getNome(), aplicativo.getCustoMensal(), aplicativo.getCodigo());
    }

    @Override
    public void delete(Long codigo) {
        String sql = "DELETE FROM aplicativos WHERE codigo = ?";
        jdbcTemplate.update(sql, codigo);
    }
    
}
