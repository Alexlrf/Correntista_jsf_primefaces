package br.com.correntista.dao;

import br.com.correntista.entidade.Perfil;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author AlexLRF
 */
public class PerfilDaoImpl extends BaseDaoImpl<Perfil, Long> implements PerfilDao, Serializable {

    @Override
    public Perfil pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Perfil) sessao.get(Perfil.class, id);
    }

    @Override
    public List<Perfil> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("FROM Perfil WHERE nome LIKE :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Perfil> pesquisarTodo(Session session) throws HibernateException {
        return session.createQuery("FROM Perfil").list();
    }

}
