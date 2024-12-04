package cafeteria.vendas;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import javax.swing.JOptionPane;

import cafeteria.DbConn;
import cafeteria.vendas.produtos.EstoqueProduto;
import cafeteria.vendas.produtos.Produto;
import cafeteria.vendas.produtos.UnidadeMedida;

public class VendaService implements IVendaService{
    Connection conn = new DbConn().getConnection();
    int id;
    UnidadeMedida medidaConvertida;
    int medidaInt;
    String nome;
    int medida;
    double preco;
    int estoque;
    String medidaString;


    @Override
    public List<Produto> procurarProduto() {

        List<Produto> produtos = new ArrayList<>();
        
        String sql = "SELECT id, nome, medida, preco, estoque FROM produto ORDER BY id";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                nome = rs.getString("nome");
                medida = rs.getInt("medida");
                conversaoMedida(medida);
                preco = rs.getDouble("preco");
                estoque = rs.getInt("estoque");
                Produto produto = new EstoqueProduto(nome, medidaConvertida, preco, estoque, id);
                produtos.add(produto);
            }
            rs.close();
            ps.close();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return produtos;
    }


    public Object conversaoMedida(int medida){
        switch (medida){
            case 1: 
                medidaConvertida = UnidadeMedida.UNIDADE;
                break;
            case 2:
                medidaConvertida = UnidadeMedida.FATIA;
                break;
            case 3:
                medidaConvertida = UnidadeMedida.GARRAFA;
                break;
            case 4:
                medidaConvertida = UnidadeMedida.LATA;
                break;
            case 5:
                medidaConvertida = UnidadeMedida.LITRO;
                break;
            case 6:
                medidaConvertida = UnidadeMedida.PACOTE;
                break;
            default:
                JOptionPane.showMessageDialog(null, "Não há medida.");
                break;  
        }

        return medidaConvertida;
    }

    public int conversaoMedida(UnidadeMedida medida){
        switch (medida){
            case UNIDADE: 
                medidaInt = 1;
                break;
            case FATIA:
                medidaInt = 2;
                break;
            case GARRAFA:
                medidaInt = 3;
                break;
            case LATA:
                medidaInt = 4;
                break;
            case LITRO:
                medidaInt = 5;
                break;
            case PACOTE:
                medidaInt = 6;
                break;
            default:
                JOptionPane.showMessageDialog(null, "Não há medida.");
                break;  
        }

        return medidaInt;
    }

    public String conversaoMedidaString(UnidadeMedida medida){
        switch (medida){
            case UNIDADE: 
                medidaString = "Unidade";
                break;
            case FATIA:
                medidaString = "Fatia";
                break;
            case GARRAFA:
                medidaString = "Garrafa";
                break;
            case LATA:
                medidaString = "Lata";
                break;
            case LITRO:
                medidaString = "Litro";
                break;
            case PACOTE:
                medidaString = "Litro";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Não há medida.");
                break;  
        }

        return medidaString;
    }

}
