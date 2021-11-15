package br.com.correntista.controle;

import br.com.correntista.entidade.Moeda;
import br.com.correntista.util.Cotacao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author AlexLRF
 */
@ManagedBean(name = "moedaC")
@ViewScoped
public class MoedaControle implements Serializable {

    List<Moeda> moedas;

    public MoedaControle() {
        moedas = Cotacao.buscaCotacao();
    }

    public List<Moeda> getMoedas() {
        if (moedas == null) {
            moedas = new ArrayList();
        }
        return moedas;
    }

    public void setMoedas(List<Moeda> moedas) {
        this.moedas = moedas;
    }

}
