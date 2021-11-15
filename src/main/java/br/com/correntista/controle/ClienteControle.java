package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PessoaFisicaDao;
import br.com.correntista.dao.PessoaFisicaDaoImpl;
import br.com.correntista.entidade.PessoaFisica;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author AlexLRF
 */
@ManagedBean(name = "clienteC")
public class ClienteControle {

    private PessoaFisica pessoaFisica;
    private PessoaFisicaDao fisicaDao;

    public String salvar() {
        fisicaDao = new PessoaFisicaDaoImpl();
        Session sessao = HibernateUtil.abrirSessao();

        FacesContext contexto = FacesContext.getCurrentInstance();
        FacesMessage message = null;
        try {
            fisicaDao.salvarOuAlterar(pessoaFisica, sessao);
            message = new FacesMessage("Salvo com sucesso!", "");
        } catch (HibernateException e) {
            message = new FacesMessage("Erro ao Salvar!", "");

        } finally {
            sessao.close();
            contexto.addMessage(null, message);
        }

        return "mostra";
    }

//    getters setters
    public PessoaFisica getPessoaFisica() {
        if (pessoaFisica == null) {
            pessoaFisica = new PessoaFisica();
        }
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

}
