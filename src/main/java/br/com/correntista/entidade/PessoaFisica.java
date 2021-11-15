package br.com.correntista.entidade;

import javax.persistence.*;

/**
 *
 * @author AlexLRF
 */
@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class PessoaFisica extends Cliente {

    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private String rg;

    @ManyToOne
    @JoinColumn(name = "id_profissao")
    private Profissao profissao;

    public PessoaFisica() {
    }

    public PessoaFisica(Long id, String nome, String email, String cpf, String rg) {
        super(id, nome, email);
        this.cpf = cpf;
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

}
