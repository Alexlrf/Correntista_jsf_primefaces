package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.ProfissaoDao;
import br.com.correntista.dao.ProfissaoDaoImpl;
import br.com.correntista.entidade.Profissao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author AlexLRF
 */
@ManagedBean(name = "profissaoC")
@ViewScoped
public class ProfissaoControle {

    private DataModel<Profissao> modelProfissoes;
    private List<Profissao> profissaos;
    private ProfissaoDao profissaoDao;
    private Profissao profissao;
    private Session sessao;
    private int aba;

    public ProfissaoControle() {
        profissaoDao = new ProfissaoDaoImpl();
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            profissaos = profissaoDao.pesquisarPorNome(profissao.getNome(), sessao);
            modelProfissoes = new ListDataModel<>(profissaos);
            profissao.setNome(null);
        } catch (HibernateException e) {
            System.out.println("Erro ao pesquisar " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        sessao = HibernateUtil.abrirSessao();
        try {
            profissaoDao.salvarOuAlterar(profissao, sessao);
            Mensagem.enviarSucesso("Profissão: " + profissao.getNome() + " - Salvo com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Profissão: " + profissao.getNome() + " - Erro ao salvar!");
            System.out.println("método salvar Profissão - erro " + e.getMessage());
        } finally {
            modelProfissoes = null;
            profissao = null;
            sessao.close();
        }
    }

    public void excluir() {
        profissao = modelProfissoes.getRowData();
        sessao = HibernateUtil.abrirSessao();
        try {
            profissaoDao.excluir(profissao, sessao);
            profissao = null;
            Mensagem.enviarSucesso("Profissão: " + profissao.getNome() + " - Excluído com sucesso!");
            modelProfissoes = null;
        } catch (HibernateException e) {
            Mensagem.enviarErro("Profissão: " + profissao.getNome() + " - Erro ao excluir!");
            System.out.println("método excluir Profissão - erro " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void prepararAlterar() {
        profissao = modelProfissoes.getRowData();
        aba = 1;
    }

//    getters e setters
    public Profissao getProfissao() {
        if (profissao == null) {
            profissao = new Profissao();
        }
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public DataModel<Profissao> getModelProfissoes() {
        return modelProfissoes;
    }

    public int getAba() {
        return aba;
    }

}
