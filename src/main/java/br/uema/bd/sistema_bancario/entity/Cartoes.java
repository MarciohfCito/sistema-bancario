package br.uema.bd.sistema_bancario.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartoes")
public class Cartoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cartao")
    private Integer idCartao;

    @ManyToOne
    @JoinColumn(name = "id_conta", nullable = false)
    private Contas conta;

    @Column(name = "numero_hash")
    private String numeroHash;

    @Column(name = "nome_impresso")
    private String nomeImpresso;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Column(name = "cvv_hash")
    private String cvvHash;

    @Column(name = "tipo_cartao")
    private String tipoCartao;

    @Column(name = "status")
    private String status;

    public Integer getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(Integer idCartao) {
        this.idCartao = idCartao;
    }

    public Contas getConta() {
        return conta;
    }

    public void setConta(Contas conta) {
        this.conta = conta;
    }

    public String getNumeroHash() {
        return numeroHash;
    }

    public void setNumeroHash(String numeroHash) {
        this.numeroHash = numeroHash;
    }

    public String getNomeImpresso() {
        return nomeImpresso;
    }

    public void setNomeImpresso(String nomeImpresso) {
        this.nomeImpresso = nomeImpresso;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getCvvHash() {
        return cvvHash;
    }

    public void setCvvHash(String cvvHash) {
        this.cvvHash = cvvHash;
    }

    public String getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}