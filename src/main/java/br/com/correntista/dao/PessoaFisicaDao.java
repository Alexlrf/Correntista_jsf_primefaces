package br.com.correntista.dao;

import br.com.correntista.entidade.PessoaFisica;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public interface PessoaFisicaDao extends BaseDao<PessoaFisica, Long> {

    List<PessoaFisica> pesquisarPorNome(String nome, Session sessao) throws HibernateException;

    PessoaFisica pesquisarPorCpf(String cpf, Session sessao) throws HibernateException;
}
