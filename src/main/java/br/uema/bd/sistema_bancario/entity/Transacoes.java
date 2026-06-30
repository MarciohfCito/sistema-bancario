package br.uema.bd.sistema_bancario.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transacoes")
public class Transacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transacao")
    private Integer idTransacao;

    @ManyToOne
    @JoinColumn(name = "id_conta_origem")
    private Contas contaOrigem;

    @ManyToOne
    @JoinColumn(name = "id_conta_destino")
    private Contas contaDestino;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "tipo_transacao")
    private String tipoTransacao;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "status")
    private String status;

    public Integer getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Integer idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Contas getContaOrigem() {
        return contaOrigem;
    }

    public void setContaOrigem(Contas contaOrigem) {
        this.contaOrigem = contaOrigem;
    }

    public Contas getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Contas contaDestino) {
        this.contaDestino = contaDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(String tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}