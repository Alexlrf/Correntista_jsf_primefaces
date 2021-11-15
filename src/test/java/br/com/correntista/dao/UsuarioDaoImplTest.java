package br.com.correntista.dao;

import br.com.correntista.entidade.Usuario;
import br.com.correntista.util.UtilGerador;
import static br.com.correntista.util.UtilGerador.gerarCaracter;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AlexLRF
 */
public class UsuarioDaoImplTest {
    
    private Usuario usuario;
    private UsuarioDao usuarioDao;
    private Session sessao;

    public UsuarioDaoImplTest() {
        usuarioDao = new UsuarioDaoImpl();
    }
    
    @Test
    public void testSalvar() {
        System.out.println("salvar");
        usuario = new Usuario(UtilGerador.gerarNome(), UtilGerador.gerarCaracter(4), UtilGerador.gerarCaracter(4), null);
        sessao = HibernateUtil.abrirSessao();
        usuarioDao.salvarOuAlterar(usuario, sessao);
        sessao.close();
        assertNotNull(usuario.getId());
    }
    
    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarUsuarioBD();
        usuario.setLogin(gerarCaracter(4));
        sessao = HibernateUtil.abrirSessao();
        usuarioDao.salvarOuAlterar(usuario, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        Usuario usuarioAlt = usuarioDao.pesquisarPorId(usuario.getId(), sessao);
        sessao.close();
        assertEquals(usuario.getLogin(), usuarioAlt.getLogin());
    }
    
    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscarUsuarioBD();
        sessao = HibernateUtil.abrirSessao();
        usuarioDao.excluir(usuario, sessao);
        Usuario usuarioExc = usuarioDao.pesquisarPorId(usuario.getId(), sessao);
        sessao.close();
        assertNull(usuarioExc);
    }

    @Test
    public void testPesquisarPorID() {
        System.out.println("pesquisarPorid");
        buscarUsuarioBD();
        sessao = HibernateUtil.abrirSessao();
        Usuario usuarioId = usuarioDao.pesquisarPorId(usuario.getId(), sessao);
        sessao.close();
        assertNotNull(usuarioId);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarUsuarioBD();
        sessao = HibernateUtil.abrirSessao();
        List<Usuario> usuarios = usuarioDao.pesquisarPorNome(usuario.getNome().substring(0, 4), sessao);
        sessao.close();
        assertTrue(usuarios.size() > 0);
    }
    
    public Usuario buscarUsuarioBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Usuario");
        List<Usuario> usuarios = consulta.list();
        sessao.close();
        if (usuarios.isEmpty()) {
            testSalvar();
        } else {
            usuario = usuarios.get(0);
        }
        return usuario;
    }

}
