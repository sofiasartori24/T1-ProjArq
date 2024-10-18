package com.T1.projArq.infrastructure.dataBase;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Pagamento;
import com.T1.projArq.domain.repository.IPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
        String sql = "INSERT INTO pagamentos (codigo, valorPago, dataPagamento, promocao, idAssinatura) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, codigo, valorPago, dataPagamento, promocao, idAssinatura);

        Assinatura assinatura = getAssinaturaById(idAssinatura);

        return new Pagamento(codigo, valorPago, dataPagamento, promocao, assinatura);
    }

    @Override
    public List<Pagamento> getAll() {
        String sql = "SELECT * FROM pagamentos";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            double valorPago = rs.getDouble("valorPago");
            Date dataPagamento = rs.getDate("dataPagamento");
            String promocao = rs.getString("promocao");
            Long idAssinatura = rs.getLong("idAssinatura");

            Assinatura assinatura = getAssinaturaById(idAssinatura);

            return new Pagamento(codigo, valorPago, dataPagamento, promocao, assinatura);
        });
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
            Long aplicativoId = rs.getLong("aplicativo_id");

            Aplicativo aplicativo = getAplicativoById(aplicativoId);

            Assinatura assinatura = new Assinatura(codigo, inicioVigencia, aplicativo);
            assinatura.setFimVigencia(fimVigencia);

            return assinatura;
        });
    }

    private Aplicativo getAplicativoById(Long aplicativoId) {
        String sql = "SELECT * FROM aplicativos WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{aplicativoId}, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            String nome = rs.getString("nome");
            double custo = rs.getDouble("custo");

            return new Aplicativo(codigo, nome, custo);
        });
    }
}
