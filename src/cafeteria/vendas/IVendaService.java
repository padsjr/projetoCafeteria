package cafeteria.vendas;

import java.awt.List;
import java.lang.reflect.Array;
import java.util.ArrayList;

import cafeteria.vendas.produtos.Produto;
import cafeteria.vendas.produtos.UnidadeMedida;

public interface IVendaService {

    public java.util.List procurarProduto();

    public Object conversaoMedida(int medida);

    public int conversaoMedida(UnidadeMedida medida);

    public String conversaoMedidaString(UnidadeMedida medida);
}
