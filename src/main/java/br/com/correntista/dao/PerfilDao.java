package br.com.correntista.dao;

import br.com.correntista.entidade.Perfil;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public interface PerfilDao extends BaseDao<Perfil, Long> {

    List<Perfil> pesquisarPorNome(String nome, Session session) throws HibernateException;

    List<Perfil> pesquisarTodo(Session session) throws HibernateException;

}
