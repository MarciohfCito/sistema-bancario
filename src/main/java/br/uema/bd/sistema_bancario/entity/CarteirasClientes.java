package br.uema.bd.sistema_bancario.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carteiras_clientes")
public class CarteirasClientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carteira")
    private Integer id_carteira;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Clientes cliente;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private ProdutosInvestimento produto;

    @Column(name = "quantidade")
    private BigDecimal quantidade;

    @Column(name = "valor_aplicado")
    private BigDecimal valor_aplicado;

    public Integer getId_carteira() {
        return id_carteira;
    }

    public void setId_carteira(Integer id_carteira) {
        this.id_carteira = id_carteira;
    }

    public Clientes getClientes() {
        return cliente;
    }

    public void setClientes(Clientes cliente) {
        this.cliente = cliente;
    }

    public ProdutosInvestimento getProduto() {
        return produto;
    }

    public void setProduto(ProdutosInvestimento produto) {
        this.produto = produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor_aplicado() {
        return valor_aplicado;
    }

    public void setValor_aplicado(BigDecimal valor_aplicado) {
        this.valor_aplicado = valor_aplicado;
    }
}
