package br.com.correntista.dao;

import br.com.correntista.entidade.Profissao;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public class ProfissaoDaoImpl extends BaseDaoImpl<Profissao, Long>
        implements ProfissaoDao, Serializable {

    @Override
    public Profissao pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Profissao) sessao.get(Profissao.class, id);
    }

    @Override
    public List<Profissao> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("FROM Profissao where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Profissao> pesquisarTodo(Session sessao) throws HibernateException {
        return sessao.createQuery("FROM Profissao order by nome").list();
    }

}
