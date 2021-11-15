package br.com.correntista.dao;

import br.com.correntista.entidade.Cartao;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public class CartaoDaoImpl extends BaseDaoImpl<Cartao, Long> implements CartaoDao, Serializable {

    @Override
    public Cartao pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Cartao) sessao.get(Cartao.class, id);
    }

    @Override
    public Cartao pesquisarPorNumero(String numero, Session session) throws HibernateException {
        Query consulta = session.createQuery("FROM Cartao where numero = :numero");
        consulta.setParameter("numero", numero);
        return (Cartao) consulta.uniqueResult();
    }

}
