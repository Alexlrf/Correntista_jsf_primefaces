package br.com.correntista.dao;

import br.com.correntista.entidade.Usuario;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public interface UsuarioDao extends BaseDao<Usuario, Long> {

    List<Usuario> pesquisarPorNome(String nome, Session session) throws HibernateException;
}
