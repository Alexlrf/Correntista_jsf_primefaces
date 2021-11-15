package br.com.correntista.dao;

import br.com.correntista.entidade.PessoaJuridica;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public interface PessoaJuridicaDao extends BaseDao<PessoaJuridica, Long> {

    List<PessoaJuridica> pesquisarPorNome(String nome, Session sessao) throws HibernateException;
}
