package br.uema.bd.sistema_bancario.entity;

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
@Table(name = "carteiras_clientes")
public class CarteirasClientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carteira")
    private Integer id_carteira;
    
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Clientes Clientes;

    @OneToMany
    @JoinColumn(name = "id_produto")
    private ProdutosInvestimento Produtos;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor_aplicado")
    private Double valor_aplicado;

    public Integer getId_carteira() {
        return id_carteira;
    }

    public void setId_carteira(Integer id_carteira) {
        this.id_carteira = id_carteira;
    }

    public Clientes getClientes() {
        return Clientes;
    }

    public void setClientes(Clientes clientes) {
        Clientes = clientes;
    }

    public ProdutosInvestimento getProdutos() {
        return Produtos;
    }

    public void setProdutos(ProdutosInvestimento produtos) {
        Produtos = produtos;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor_aplicado() {
        return valor_aplicado;
    }

    public void setValor_aplicado(Double valor_aplicado) {
        this.valor_aplicado = valor_aplicado;
    }
}
