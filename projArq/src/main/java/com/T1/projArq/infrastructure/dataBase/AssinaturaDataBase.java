package com.T1.projArq.infrastructure.dataBase;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Pagamento;
import com.T1.projArq.domain.repository.IAssinaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AssinaturaDataBase implements IAssinaturaRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AssinaturaDataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Assinatura create(Long codigo, Date inicioVigencia, Aplicativo aplicativo) {
        String sql = "INSERT INTO assinaturas (codigo, inicioVigencia, fimVigencia, aplicativo_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, codigo, inicioVigencia, null, aplicativo.getCodigo());

        return new Assinatura(codigo, inicioVigencia, aplicativo);
    }

    @Override
    public List<Assinatura> getAll() {
        String sql = "SELECT * FROM assinaturas";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            Date inicioVigencia = rs.getDate("inicioVigencia");
            Date fimVigencia = rs.getDate("fimVigencia");
            Long aplicativoId = rs.getLong("aplicativo_id");

            Aplicativo aplicativo = getAplicativoById(aplicativoId); 
            Assinatura assinatura = new Assinatura(codigo, inicioVigencia, aplicativo);
            assinatura.setFimVigencia(fimVigencia);
            //assinatura.setPagamentos(getPagamentosByAssinaturaId(codigo));

            return assinatura;
        });
    }

    @Override
    public Assinatura getById(Long codigo) {
        String sql = "SELECT * FROM assinaturas WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{codigo}, (rs, rowNum) -> {
            Date inicioVigencia = rs.getDate("inicioVigencia");
            Date fimVigencia = rs.getDate("fimVigencia");
            Long aplicativoId = rs.getLong("aplicativo_id");

            Aplicativo aplicativo = getAplicativoById(aplicativoId);
            Assinatura assinatura = new Assinatura(codigo, inicioVigencia, aplicativo);
            assinatura.setFimVigencia(fimVigencia);
            //assinatura.setPagamentos(getPagamentosByAssinaturaId(codigo));

            return assinatura;
        });
    }

    @Override
    public void update(Assinatura assinatura) {
        String sql = "UPDATE assinaturas SET inicioVigencia = ?, fimVigencia = ?, aplicativo_id = ? WHERE codigo = ?";
        jdbcTemplate.update(sql, assinatura.getInicioVigencia(), assinatura.getFimVigencia(), assinatura.getAplicativo().getCodigo(), assinatura.getCodigo());
    }

    @Override
    public void delete(Long codigo) {
        String sql = "DELETE FROM assinaturas WHERE codigo = ?";
        jdbcTemplate.update(sql, codigo);
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
    private List<Pagamento> getPagamentosByAssinaturaId(Long assinaturaId) {
        String sql = "SELECT * FROM pagamentos WHERE assinatura_id = ?";
        return jdbcTemplate.query(sql, new Object[]{assinaturaId}, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            double valorPago = rs.getDouble("valorPago");
            Date dataPagamento = rs.getDate("dataPagamento");
            String promocao = rs.getString("promocao");

            Assinatura assinatura = getAssinaturaById(assinaturaId);
            return new Pagamento(codigo, valorPago, dataPagamento, promocao, assinatura);
        });
    }

    public Assinatura getAssinaturaById(Long id) {
        String sql = "SELECT * FROM assinaturas WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            Date inicioVigencia = rs.getDate("inicio_vigencia");
            Date fimVigencia = rs.getDate("fim_vigencia");
            Long aplicativoId = rs.getLong("aplicativo_id");

            Aplicativo aplicativo = getAplicativoById(aplicativoId);
            return new Assinatura(codigo, inicioVigencia, aplicativo);
        });
    }



}
