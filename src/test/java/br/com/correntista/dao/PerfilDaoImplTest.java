package br.com.correntista.dao;

import br.com.correntista.entidade.Perfil;
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
public class PerfilDaoImplTest {

    private Perfil perfil;
    private PerfilDao perfilDao;
    private Session sessao;

    public PerfilDaoImplTest() {
        perfilDao = new PerfilDaoImpl();
    }

    @Test
    public void testPesquisarPorID() {
        System.out.println("pesquisarPorid");
        buscarPerfilBD();
        sessao = HibernateUtil.abrirSessao();
        Perfil perfilId = perfilDao.pesquisarPorId(perfil.getId(), sessao);
        sessao.close();
        assertNotNull(perfilId);
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        perfil = new Perfil();
        perfil.setNome(UtilGerador.gerarNome());
        perfil.setDescricao("perfil para teste unitário");
        sessao = HibernateUtil.abrirSessao();
        perfilDao.salvarOuAlterar(perfil, sessao);
        sessao.close();
        assertNotNull(perfil.getId());
    }

    // @Test  não pode ser excluído por ter chave estrangeira
    public void testExcluir() {
        System.out.println("excluir");
        buscarPerfilBD();
        sessao = HibernateUtil.abrirSessao();
        perfilDao.excluir(perfil, sessao);
        Perfil perfilExc = perfilDao.pesquisarPorId(perfil.getId(), sessao);
        sessao.close();
        assertNull(perfilExc);
    }

    @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarPerfilBD();
        perfil.setDescricao(gerarCaracter(18));
        sessao = HibernateUtil.abrirSessao();
        perfilDao.salvarOuAlterar(perfil, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        Perfil perfilAlt = perfilDao.pesquisarPorId(perfil.getId(), sessao);
        sessao.close();
        assertEquals(perfil.getDescricao(), perfilAlt.getDescricao());
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarPerfilBD();
        sessao = HibernateUtil.abrirSessao();
        List<Perfil> perfis = perfilDao.pesquisarPorNome(perfil.getNome().substring(0, 4), sessao);
        sessao.close();
        assertTrue(perfis.size() > 0);
    }

    @Test
    public void testPesquisarTodo() {
        System.out.println("pesquisarTodo");
        sessao = HibernateUtil.abrirSessao();
        List<Perfil> listaPerfis = perfilDao.pesquisarTodo(sessao);
        sessao.close();
        assertTrue(!listaPerfis.isEmpty());
    }

    public Perfil buscarPerfilBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from Perfil");
        List<Perfil> perfis = consulta.list();
        sessao.close();
        if (perfis.isEmpty()) {
            testSalvar();
        } else {
            perfil = perfis.get(0);
        }
        return perfil;
    }

}
