package cafeteria.vendas.produtos;

//import java.util.List;

public interface IProdutoService {

    public void adicionarProduto(EstoqueProduto produto);

    public void editarProduto(EstoqueProduto produto);

    public Produto procurarProduto(int id);
}
