package com.T1.projArq.domain.repository;

import com.T1.projArq.domain.model.Usuario;

public interface IUsuarioRepository {
    Usuario create(String nome, String email);
    Usuario get();
    void update(Usuario pagamento);
    void delete();
}
