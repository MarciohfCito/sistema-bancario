package br.uema.bd.sistema_bancario.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "chaves_pix")
public class ChavesPix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chave")
    private Integer idChave;

    @ManyToOne
    @JoinColumn(name = "id_conta", nullable = false)
    private Contas conta;

    @Column(name = "tipo_chave", nullable = false)
    private String tipoChave;

    @Column(name = "valor_chave", nullable = false, unique = true)
    private String valorChave;

    public Integer getIdChave() {
        return idChave;
    }

    public void setIdChave(Integer idChave) {
        this.idChave = idChave;
    }

    public Contas getConta() {
        return conta;
    }

    public void setConta(Contas conta) {
        this.conta = conta;
    }

    public String getTipoChave() {
        return tipoChave;
    }

    public void setTipoChave(String tipoChave) {
        this.tipoChave = tipoChave;
    }

    public String getValorChave() {
        return valorChave;
    }

    public void setValorChave(String valorChave) {
        this.valorChave = valorChave;
    }

}