package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PerfilDao;
import br.com.correntista.dao.PerfilDaoImpl;
import br.com.correntista.entidade.Perfil;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author AlexLRF
 */
@ManagedBean(name = "perfilC")
@ViewScoped
public class PerfilControle {

    private DataModel<Perfil> modelPerfis;
    private List<Perfil> perfis;
    private PerfilDao perfilDao;
    private Session sessao;
    private Perfil perfil;
    private int aba;

    public PerfilControle() {
        perfilDao = new PerfilDaoImpl();
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            perfis = perfilDao.pesquisarPorNome(perfil.getNome(), sessao);
            modelPerfis = new ListDataModel<>(perfis);
        } catch (HibernateException e) {
            System.out.println("método pesquisarPorNome Perfil - erro " + e.getMessage());
        } finally {
            perfil.setNome(null);
            sessao.close();
        }
    }

    public void salvar() {
        sessao = HibernateUtil.abrirSessao();
        try {
            perfilDao.salvarOuAlterar(perfil, sessao);
            Mensagem.enviarSucesso("Perfil: " + perfil.getNome() + " - Salvo com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Perfil: " + perfil.getNome() + " - Erro ao salvar!");
            System.out.println("método salvar Perfil - erro " + e.getMessage());
        } finally {
            perfil = null;
            aba = 0;
            sessao.close();
        }
    }
    
    public void excluir() {
        perfil = modelPerfis.getRowData();
        sessao = HibernateUtil.abrirSessao();
        try {
            perfilDao.excluir(perfil, sessao);
            Mensagem.enviarSucesso("Perfil: " + perfil.getNome() + " - Excluído com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Perfil: " + perfil.getNome() + " - Erro ao excluir!");
            System.out.println("método excluir Perfil - erro " + e.getMessage());
        } finally {
            modelPerfis = null;
            perfil = null;
            sessao.close();
        }
    }

    public void prepararAlterar() {
        perfil = modelPerfis.getRowData();
        aba = 1;
    }

//    getters e setters
    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public List<Perfil> getPerfis() {
        return perfis;
    }

    public DataModel<Perfil> getModelPerfis() {
        return modelPerfis;
    }

    public int getAba() {
        return aba;
    }
    
    

}
