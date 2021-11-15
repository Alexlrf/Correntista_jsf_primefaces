package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaFisicaDao;
import br.com.correntista.dao.PessoaFisicaDaoImpl;
import br.com.correntista.dao.ProfissaoDao;
import br.com.correntista.dao.ProfissaoDaoImpl;
import br.com.correntista.entidade.PessoaFisica;
import br.com.correntista.entidade.Profissao;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author AlexLRF
 */
@ManagedBean(name = "pfisicaC")
@ViewScoped
public class PessoaFisicaControle {

    private DataModel<PessoaFisica> modelFisicas;
    private List<SelectItem> comboProfissoes;
    private List<PessoaFisica> pessoaFisicas;
    private PessoaFisica pessoaFisica;
    private PessoaFisicaDao fisicaDao;
    private Profissao profissao;
    private Session sessao;
    private int aba;

    public PessoaFisicaControle() {
        fisicaDao = new PessoaFisicaDaoImpl();
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            pessoaFisicas = fisicaDao.pesquisarPorNome(pessoaFisica.getNome(), sessao);
            modelFisicas = new ListDataModel<>(pessoaFisicas);
        } catch (HibernateException e) {
            System.out.println("método pesquisarPorNome PessoaFisica - erro " + e.getMessage());
        } finally {
            pessoaFisica.setNome(null);
            sessao.close();
        }
    }

    public void carregarComboProfissao() {
        sessao = HibernateUtil.abrirSessao();
        ProfissaoDao profissaoDao = new ProfissaoDaoImpl();
        try {
            List<Profissao> profissoes = profissaoDao.pesquisarTodo(sessao);
            comboProfissoes = new ArrayList<>();
            for (Profissao prof : profissoes) {
                comboProfissoes.add(new SelectItem(prof.getId(), prof.getNome()));
            }
        } catch (HibernateException e) {
            System.out.println("método pesquisarPorNome PessoaFisica - erro " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("Novo")) {
            carregarComboProfissao();
        }
    }

    public void onTabClose(TabCloseEvent event) {
    }

    public void salvar() {
        sessao = HibernateUtil.abrirSessao();
        try {
            pessoaFisica.setProfissao(profissao);
            fisicaDao.salvarOuAlterar(pessoaFisica, sessao);
            Mensagem.enviarSucesso("Pessoa Física: " + pessoaFisica.getNome() + " - Salva com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Pessoa Física: " + pessoaFisica.getNome() + " - Erro ao salvar!");
            System.out.println("método salvar PessoaFisica - erro " + e.getMessage());
        } finally {
            pessoaFisica = null;
            modelFisicas = null;
            sessao.close();
        }
    }

    public void excluir() {
        pessoaFisica = modelFisicas.getRowData();
        sessao = HibernateUtil.abrirSessao();
        try {
            fisicaDao.excluir(pessoaFisica, sessao);
            Mensagem.enviarSucesso("Pessoa Física: " + pessoaFisica.getNome() + " - Excluída com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Pessoa Física: " + pessoaFisica.getNome() + " - Erro ao excluir!");
            System.out.println("método excluir PessoaFisica - erro " + e.getMessage());
        } finally {
            pessoaFisica = null;
            modelFisicas = null;
            sessao.close();
        }
    }

    public void prepararAlterar() {
        pessoaFisica = modelFisicas.getRowData();
        profissao = pessoaFisica.getProfissao();
        aba = 1;
    }

//    getters e setters
    public PessoaFisica getPessoaFisica() {
        if (pessoaFisica == null) {
            pessoaFisica = new PessoaFisica();
        }
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public DataModel<PessoaFisica> getModelFisicas() {
        return modelFisicas;
    }

    public int getAba() {
        return aba;
    }

    public List<SelectItem> getComboProfissoes() {
        return comboProfissoes;
    }

    public Profissao getProfissao() {
        if (profissao == null) {
            profissao = new Profissao();
        }
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

}
