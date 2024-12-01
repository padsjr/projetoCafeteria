package cafeteria.vendas.produtos;

public class EstoqueProduto extends Produto {

    int estoque;
    
    public EstoqueProduto(String nome, UnidadeMedida medida, double preco, int estoque) {
            this.nome = nome;
            this.medida = medida;
        this.preco = preco;
        this.estoque = estoque;
    } 

    public EstoqueProduto(String nome, UnidadeMedida medida, double preco, int estoque, int id){
        this(nome, medida, preco, estoque);
        this.id = id;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    
    

}
