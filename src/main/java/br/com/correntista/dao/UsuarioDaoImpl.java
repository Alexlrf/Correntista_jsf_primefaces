package br.com.correntista.dao;

import br.com.correntista.entidade.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

/**
 *
 * @author AlexLRF
 */
public class UsuarioDaoImpl extends BaseDaoImpl<Usuario, Long> implements UsuarioDao, Serializable {

    @Override
    public Usuario pesquisarPorId(Long id, Session sessao) throws HibernateException {
        return (Usuario) sessao.get(Usuario.class, id);
    }

    @Override
    public List<Usuario> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("FROM Usuario where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

}
