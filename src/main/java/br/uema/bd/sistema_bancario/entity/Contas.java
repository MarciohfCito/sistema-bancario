package br.uema.bd.sistema_bancario.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "contas")
public class Contas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    private Integer idConta;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Clientes cliente;

    @Column(name = "numero_conta", unique = true)
    private String numeroConta;

    @Column(name = "agencia")
    private String agencia;

    @Column(name = "tipo_conta")
    private String tipoConta;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "limite_especial")
    private BigDecimal limiteEspecial;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "conta")
    private List<Cartoes> cartoes;

    @OneToMany(mappedBy = "conta")
    private List<ChavesPix> chavesPix;

    @OneToMany(mappedBy = "conta")
    private List<OrdemInvestimento> ordensInvestimento;

    @OneToMany(mappedBy = "contaOrigem")
    private List<Transacoes> transacoesOrigem;

    @OneToMany(mappedBy = "contaDestino")
    private List<Transacoes> transacoesDestino;

    public Integer getIdConta() {
        return idConta;
    }

    public void setIdConta(Integer idConta) {
        this.idConta = idConta;
    }

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public BigDecimal getLimiteEspecial() {
        return limiteEspecial;
    }

    public void setLimiteEspecial(BigDecimal limiteEspecial) {
        this.limiteEspecial = limiteEspecial;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Cartoes> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartoes> cartoes) {
        this.cartoes = cartoes;
    }

    public List<ChavesPix> getChavesPix() {
        return chavesPix;
    }

    public void setChavesPix(List<ChavesPix> chavesPix) {
        this.chavesPix = chavesPix;
    }

    public List<OrdemInvestimento> getOrdensInvestimento() {
        return ordensInvestimento;
    }

    public void setOrdensInvestimento(List<OrdemInvestimento> ordensInvestimento) {
        this.ordensInvestimento = ordensInvestimento;
    }

    public List<Transacoes> getTransacoesOrigem() {
        return transacoesOrigem;
    }

    public void setTransacoesOrigem(List<Transacoes> transacoesOrigem) {
        this.transacoesOrigem = transacoesOrigem;
    }

    public List<Transacoes> getTransacoesDestino() {
        return transacoesDestino;
    }

    public void setTransacoesDestino(List<Transacoes> transacoesDestino) {
        this.transacoesDestino = transacoesDestino;
    }

}