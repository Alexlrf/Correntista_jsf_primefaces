package br.com.correntista.dao;

import br.com.correntista.entidade.PessoaJuridica;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public class PessoaJuridicaDaoImpl extends BaseDaoImpl<PessoaJuridica, Long>
        implements PessoaJuridicaDao, Serializable {

    @Override
    public PessoaJuridica pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (PessoaJuridica) sessao.get(PessoaJuridica.class, id);
    }

    @Override
    public List<PessoaJuridica> pesquisarPorNome(String nome, Session sessao) throws HibernateException {
        Query consulta = sessao.createQuery("FROM PessoaJuridica WHERE nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
}
