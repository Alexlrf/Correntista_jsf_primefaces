package br.com.correntista.dao;

import br.com.correntista.entidade.PessoaJuridica;
import static br.com.correntista.util.UtilGerador.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AlexLRF
 */
public class PessoaJuridicaDaoImplTest {

    private PessoaJuridica juridica;
    private PessoaJuridicaDao juridicaDao;
    private Session sessao;

    public PessoaJuridicaDaoImplTest() {
        juridicaDao = new PessoaJuridicaDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        juridica = new PessoaJuridica(null, gerarNome(), gerarEmail(), gerarCnpj(), gerarCaracter(10));
        sessao = HibernateUtil.abrirSessao();
        juridicaDao.salvarOuAlterar(juridica, sessao);
        sessao.close();
        assertNotNull(juridica.getId());

    }

   @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscaPessoaJuridicaBD();
        juridica.setEmail(gerarEmail());
        sessao = HibernateUtil.abrirSessao();
        juridicaDao.salvarOuAlterar(juridica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        PessoaJuridica juridicaAlt = juridicaDao.pesquisarPorId(juridica.getId(), sessao);
        sessao.close();
        assertEquals(juridicaAlt.getEmail(), juridica.getEmail());

    }

    @Test
    public void testExcluir() {
        System.out.println("excluir");
        buscaPessoaJuridicaBD();
        sessao = HibernateUtil.abrirSessao();
        juridicaDao.excluir(juridica, sessao);

        PessoaJuridica juridicaExc = juridicaDao.pesquisarPorId(juridica.getId(), sessao);
        sessao.close();
        assertNull(juridicaExc);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscaPessoaJuridicaBD();
        sessao = HibernateUtil.abrirSessao();
        List<PessoaJuridica> pessoas = juridicaDao.pesquisarPorNome(juridica.getNome().substring(0, 4), sessao);
        sessao.close();
        assertTrue(!pessoas.isEmpty());
    }

    public PessoaJuridica buscaPessoaJuridicaBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from PessoaJuridica pj left join fetch pj.cartaos");
        List<PessoaJuridica> juridicas = consulta.list();
        sessao.close();
        if (juridicas.isEmpty()) {
            testSalvar();
        } else {
            juridica = juridicas.get(0);
        }
        return juridica;
    }

}
