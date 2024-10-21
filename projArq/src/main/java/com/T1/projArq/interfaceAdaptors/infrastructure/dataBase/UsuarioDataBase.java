package com.T1.projArq.interfaceAdaptors.infrastructure.dataBase;

import com.T1.projArq.domain.model.Usuario;
import com.T1.projArq.domain.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDataBase implements IUsuarioRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsuarioDataBase(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Usuario create(String usuario, String senha) {
        String sql = "INSERT INTO usuarios (usuario, senha) VALUES (?, ?)";
        jdbcTemplate.update(sql, usuario, senha);
        return new Usuario(usuario, senha);
    }

    @Override
    public Usuario get() {
        String sql = "SELECT * FROM usuarios LIMIT 1";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            String usuario = rs.getString("usuario");
            String senha = rs.getString("senha");
            return new Usuario(usuario, senha);
        });
    }

    @Override
    public void update(Usuario usuario) {
        String sql = "UPDATE usuarios SET usuario = ?, senha = ? WHERE id = (SELECT id FROM usuarios LIMIT 1)";
        jdbcTemplate.update(sql, usuario.getUsuario(), usuario.getSenha());
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM usuarios";
        jdbcTemplate.update(sql);
    }
}

