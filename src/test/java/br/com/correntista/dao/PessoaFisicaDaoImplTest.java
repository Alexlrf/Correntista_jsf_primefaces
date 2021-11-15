package br.com.correntista.dao;

import br.com.correntista.entidade.Endereco;
import br.com.correntista.entidade.PessoaFisica;
import br.com.correntista.entidade.Profissao;
import static br.com.correntista.util.UtilGerador.*;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author AlexLRF
 */
public class PessoaFisicaDaoImplTest {

    private PessoaFisica fisica;
    private PessoaFisicaDao fisicaDao;
    private Session sessao;

    public PessoaFisicaDaoImplTest() {
        fisicaDao = new PessoaFisicaDaoImpl();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar");
        
        ProfissaoDaoImplTest profissaoDao = new ProfissaoDaoImplTest();
        Profissao profissao = profissaoDao.buscarProfissaoBD();
        
        fisica = new PessoaFisica(null, gerarNome(), gerarEmail(), gerarCpf(), gerarNumero(6));   
        
        fisica.setProfissao(profissao);
        sessao = HibernateUtil.abrirSessao();
        
        fisicaDao.salvarOuAlterar(fisica, sessao);
        sessao.close();
        assertNotNull(fisica.getId());
    }

   @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscaPessoaFisicaBD();
        fisica.setNome(gerarNome());
        sessao = HibernateUtil.abrirSessao();
        fisicaDao.salvarOuAlterar(fisica, sessao);
        sessao.close();

        sessao = HibernateUtil.abrirSessao();
        PessoaFisica fisicaAlt = fisicaDao.pesquisarPorId(fisica.getId(), sessao);
        sessao.close();
        assertEquals(fisica.getNome(), fisicaAlt.getNome());
    }

    @Test
    public void testExcluir() { 
        System.out.println("excluir");
        buscaPessoaFisicaBD();
        sessao = HibernateUtil.abrirSessao();
        fisicaDao.excluir(fisica, sessao);

        PessoaFisica fisicaExc = fisicaDao.pesquisarPorId(fisica.getId(), sessao);
        sessao.close();

        assertNull(fisicaExc);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscaPessoaFisicaBD();
        sessao = HibernateUtil.abrirSessao();
        List<PessoaFisica> pessoas = fisicaDao.pesquisarPorNome(fisica.getNome().substring(0, 4), sessao);
        sessao.close();
        assertTrue(!pessoas.isEmpty());
    }

    @Test
    public void testPesquisarPorCpf() {
        System.out.println("pesquisarPorCpf");
        buscaPessoaFisicaBD();
        sessao = HibernateUtil.abrirSessao();
        PessoaFisica fisicaCPF = fisicaDao.pesquisarPorCpf(fisica.getCpf(), sessao);
        sessao.close();
        assertNotNull(fisicaCPF);

    }

    public PessoaFisica buscaPessoaFisicaBD() {
        sessao = HibernateUtil.abrirSessao();
        Query consulta = sessao.createQuery("from PessoaFisica");
        List<PessoaFisica> fisicas = consulta.list();
        sessao.close();
        if (fisicas.isEmpty()) {
            testSalvar();
        } else {
            fisica = fisicas.get(0);
        }
        return fisica;
    }

}
