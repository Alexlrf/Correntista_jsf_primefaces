package br.com.correntista.entidade;

import java.io.Serializable;

/**
 *
 * @author AlexLRF
 */
public class Moeda implements Serializable {

    private String nome;
    private String valorMaximo;
    private String valorMinimo;
    private String valorFinal;
    private String nomeConversao;

    public Moeda() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(String valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public String getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(String valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public String getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(String valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getNomeConversao() {
        return nomeConversao;
    }

    public void setNomeConversao(String nomeConversao) {
        this.nomeConversao = nomeConversao;
    }
}
