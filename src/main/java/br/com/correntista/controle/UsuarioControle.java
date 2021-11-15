package br.com.correntista.controle;

import br.com.correntista.dao.HibernateUtil;
import br.com.correntista.dao.PerfilDao;
import br.com.correntista.dao.PerfilDaoImpl;
import br.com.correntista.dao.UsuarioDao;
import br.com.correntista.dao.UsuarioDaoImpl;
import br.com.correntista.entidade.Perfil;
import br.com.correntista.entidade.Usuario;
import br.com.correntista.util.UtilGerador;
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
@ManagedBean(name = "usuarioC")
@ViewScoped
public class UsuarioControle {

    private DataModel<Usuario> modelUsuarios;
    private List<SelectItem> comboPerfis;
    private List<Usuario> usuarios;
    private UsuarioDao usuarioDao;
    private Usuario usuario;
    private Session sessao;
    private Perfil perfil;
    private int aba;

    public UsuarioControle() {
        usuarioDao = new UsuarioDaoImpl();
    }

    public void pesquisarPorNome() {
        sessao = HibernateUtil.abrirSessao();
        try {
            usuarios = usuarioDao.pesquisarPorNome(usuario.getNome(), sessao);
            modelUsuarios = new ListDataModel<>(usuarios);
            usuario.setNome(null);
        } catch (HibernateException e) {
            System.out.println("método pesquisarPorNome - Usuario " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void carregarComboPerfil() {
        sessao = HibernateUtil.abrirSessao();
        PerfilDao perfilDao = new PerfilDaoImpl();
        try {
            List<Perfil> perfis = perfilDao.pesquisarTodo(sessao);
            comboPerfis = new ArrayList<>();
            for (Perfil perfi : perfis) {
                comboPerfis.add(new SelectItem(perfi.getId(), perfi.getNome()));
            }
        } catch (HibernateException e) {
            System.out.println("método carregarComboPerfil - Usuario " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getTitle().equals("Novo")) {
            carregarComboPerfil();
        }
    }

    public void onTabClose(TabCloseEvent event) {
    }

    public void salvar() {
        sessao = HibernateUtil.abrirSessao();
        try {
            usuario.setSenha(UtilGerador.gerarNumero(5));
            usuario.setPerfil(perfil);
            usuarioDao.salvarOuAlterar(usuario, sessao);
            Mensagem.enviarSucesso("Usuário: " + usuario.getNome() + " - Salvo com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Usuário: " + usuario.getNome() + " - Erro ao salvar!");
            System.out.println("método salvar - Usuario " + e.getMessage());
        } finally {
            modelUsuarios = null;
            usuario = null;
            sessao.close();
        }
    }

    public void excluir() {
        usuario = modelUsuarios.getRowData();
        sessao = HibernateUtil.abrirSessao();
        try {
            usuarioDao.excluir(usuario, sessao);
            Mensagem.enviarSucesso("Usuário: " + usuario.getNome() + " - Excluído com sucesso!");
        } catch (HibernateException e) {
            Mensagem.enviarErro("Usuário: " + usuario.getNome() + " - Erro ao excluir!");
            System.out.println("método excluir - Usuario " + e.getMessage());
        } finally {
            modelUsuarios = null;
            usuario = null;
            sessao.close();
        }
    }

    public void prepararAlterar() {
        usuario = modelUsuarios.getRowData();
        perfil = usuario.getPerfil();
        aba = 1;
    }

//    getters e setters
    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public DataModel<Usuario> getModelUsuarios() {
        return modelUsuarios;
    }

    public int getAba() {
        return aba;
    }

    public List<SelectItem> getComboPerfis() {
        return comboPerfis;
    }

    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

}
