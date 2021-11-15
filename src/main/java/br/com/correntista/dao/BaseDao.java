package br.com.correntista.dao;

import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public interface BaseDao<T, ID> {

    void salvarOuAlterar(T entidade, Session sessao) throws HibernateException;

    void excluir(T entidade, Session sessao) throws HibernateException;

    T pesquisarPorId(ID id, Session sessao) throws HibernateException;
}
