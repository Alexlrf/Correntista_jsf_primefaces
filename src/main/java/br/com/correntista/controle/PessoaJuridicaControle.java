package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaJuridicaDao;
import br.com.correntista.dao.PessoaJuridicaDaoImpl;
import br.com.correntista.entidade.PessoaJuridica;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

/**
 *
 * @author AlexLRF
 */
@ManagedBean(name = "pJuridicaC")
@ViewScoped
public class PessoaJuridicaControle {

    private DataModel<PessoaJuridica> modelJuridicos;
    private List<PessoaJuridica> pessoasJuridicas;
    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaDao juridicoDao;
    private Session sessao;
    private int aba;

    public PessoaJuridicaControle() {
        juridicoDao = new PessoaJuridicaDaoImpl();
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            pessoasJuridicas = juridicoDao.pesquisarPorNome(pessoaJuridica.getNome(), sessao);
            modelJuridicos = new ListDataModel<>(pessoasJuridicas);
            pessoaJuridica.setNome(null);
        } catch (HibernateException e) {
            System.out.println("método pesquisarPorNome - PessoaJuridica - erro  " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("Novo")) {

        }
    }

    public void onTabClose(TabCloseEvent event) {
    }

    public void salvar() {
        sessao = HibernateUtil.abrirSessao();
        try {
            juridicoDao.salvarOuAlterar(pessoaJuridica, sessao);
            Mensagem.enviarSucesso("Pessoa Jurídica: " + pessoaJuridica.getNome() + " - Salvo com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Pessoa Jurídica: " + pessoaJuridica.getNome() + " - Erro ao salvar!");
            System.out.println("método salvar - PessoaJuridica - erro  " + e.getMessage());
        } finally {
            pessoaJuridica = null;
            modelJuridicos = null;
            sessao.close();
        }
    }

    public void excluir() {
        pessoaJuridica = modelJuridicos.getRowData();
        sessao = HibernateUtil.abrirSessao();
        try {
            juridicoDao.excluir(pessoaJuridica, sessao);
            Mensagem.enviarSucesso("Pessoa Jurídica: " + pessoaJuridica.getNome() + " - Excluída com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Pessoa Jurídica: " + pessoaJuridica.getNome() + " - Erro ao excluir!");
            System.out.println("método excluir - PessoaJuridica - erro " + e.getMessage());
        } finally {
            pessoaJuridica = null;
            modelJuridicos = null;
            sessao.close();
        }
    }

    public void prepararAlterar() {
        pessoaJuridica = modelJuridicos.getRowData();
        aba = 1;
    }

//    getters e setters
    public PessoaJuridica getPessoaJuridica() {
        if (pessoaJuridica == null) {
            pessoaJuridica = new PessoaJuridica();
        }
        return pessoaJuridica;
    }

    public void setPessoaJuridica(PessoaJuridica pessoaJuridica) {
        this.pessoaJuridica = pessoaJuridica;
    }

    public DataModel<PessoaJuridica> getModelJuridicos() {
        return modelJuridicos;
    }

    public int getAba() {
        return aba;
    }

}
