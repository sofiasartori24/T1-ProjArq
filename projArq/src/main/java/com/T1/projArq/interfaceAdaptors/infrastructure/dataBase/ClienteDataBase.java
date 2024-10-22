package com.T1.projArq.interfaceAdaptors.infrastructure.dataBase;

import com.T1.projArq.domain.model.Aplicativo;
import com.T1.projArq.domain.model.Assinatura;
import com.T1.projArq.domain.model.Cliente;
import com.T1.projArq.domain.model.Pagamento;
import com.T1.projArq.domain.repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ClienteDataBase implements IClienteRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClienteDataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cliente create(Long codigo, String nome, String email) {
        String sql = "INSERT INTO clientes (codigo, nome, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, codigo, nome, email);

        return new Cliente(codigo, nome, email);
    }

    @Override
    public List<Cliente> getAll() {
        String sql = "SELECT * FROM clientes";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            String nome = rs.getString("nome"); 
            String email = rs.getString("email");  

            return new Cliente(codigo, nome, email); 
        });
    }

    @Override
    public Cliente getById(Long codigo) {
        String sql = "SELECT * FROM clientes WHERE codigo = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{codigo}, (rs, rowNum) -> {
            String nome = rs.getString("nome");
            String email = rs.getString("email");
            Cliente cliente = new Cliente(codigo, nome, email);
            //cliente.setAssinaturas(getAssinaturasByClienteId(codigo));
            return cliente;
        });
    }

    @Override
    public void update(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, email = ? WHERE codigo = ?";
        jdbcTemplate.update(sql, cliente.getNome(), cliente.getEmail(), cliente.getCodigo());
    }

    @Override
    public void delete(Long codigo) {
        String sql = "DELETE FROM clientes WHERE codigo = ?";
        jdbcTemplate.update(sql, codigo);
    }

    public List<Assinatura> getAssinaturasByClienteId(Long codigoCliente) {
        String sql = "SELECT * FROM assinaturas WHERE cliente_codigo = ?";
        return jdbcTemplate.query(sql, new Object[]{codigoCliente}, (rs, rowNum) -> {
            Long codigo = rs.getLong("codigo");
            Date inicioVigencia = rs.getDate("inicio_vigencia");
            Date fimVigencia = rs.getDate("fim_vigencia");
            Long idAplicativo = rs.getLong("aplicativo_id");

            Cliente cliente = getById(codigoCliente);
            Aplicativo aplicativo = getAplicativoById(idAplicativo);
            Assinatura assinatura = new Assinatura(codigo, inicioVigencia, fimVigencia, aplicativo, cliente);
            assinatura.setFimVigencia(fimVigencia);
            assinatura.setPagamentos(getPagamentosByAssinaturaId(codigo));

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
            Long clienteId = rs.getLong("cliente_id");

            Cliente cliente = getById(clienteId);
            Aplicativo aplicativo = getAplicativoById(aplicativoId);
            return new Assinatura(codigo, inicioVigencia, fimVigencia, aplicativo, cliente);
        });
    }
}
