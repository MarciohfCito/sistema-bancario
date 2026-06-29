package br.uema.bd.sistema_bancario.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos_investimento")
public class ProdutosInvestimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "tipo_ativo", nullable = false)
    private String tipoAtivo;

    @Column(name = "emissor")
    private String emissor;

    @Column(name = "indexador_taxa")
    private String indexadorTaxa;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "produto")
    private List<CarteirasClientes> carteiras;

    @OneToMany(mappedBy = "produto")
    private List<OrdemInvestimento> ordens;

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getTipoAtivo() {
        return tipoAtivo;
    }

    public void setTipoAtivo(String tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getIndexadorTaxa() {
        return indexadorTaxa;
    }

    public void setIndexadorTaxa(String indexadorTaxa) {
        this.indexadorTaxa = indexadorTaxa;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CarteirasClientes> getCarteiras() {
        return carteiras;
    }

    public void setCarteiras(List<CarteirasClientes> carteiras) {
        this.carteiras = carteiras;
    }

    public List<OrdemInvestimento> getOrdens() {
        return ordens;
    }

    public void setOrdens(List<OrdemInvestimento> ordens) {
        this.ordens = ordens;
    }

}