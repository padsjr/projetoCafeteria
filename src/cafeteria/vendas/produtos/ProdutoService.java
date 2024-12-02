package cafeteria.vendas.produtos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import cafeteria.DbConn;

public class ProdutoService implements IProdutoService {

UnidadeMedida medidaConvertida;
EstoqueProduto produto;
int medidaInt;
Connection conn = new DbConn().getConnection();


    @Override
    public void editarProduto(EstoqueProduto produto) {
            String sql = "UPDATE produto SET nome = ?, medida = ?, preco = ?, estoque = ? where id = ? ";
            try(PreparedStatement ps = conn.prepareStatement(sql);) {
                
                ps.setString(1, produto.getNome());
                conversaoMedida(produto.getMedida());
                ps.setInt(2, medidaInt);
                ps.setDouble(3, produto.getPreco());
                ps.setInt(4, produto.getEstoque());
                ps.setInt(5, produto.getId());

                ps.executeUpdate();
                ps.close();

            } catch (SQLException e) {
                System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                e.printStackTrace();
            }
    }

    @Override
    public void adicionarProduto(EstoqueProduto produto) {
        
        try {
            
            String sql = "insert into produto(id, nome, medida, preco, estoque) values (?, ?, ? , ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            conversaoMedida(produto.getMedida());
            ps.setInt(1, produto.getId());
            ps.setString(2, produto.getNome());
            ps.setInt(3, medidaInt); 
            ps.setDouble(4, produto.getPreco());
            ps.setInt(5, produto.getEstoque());
    
            ps.execute();
            ps.close();
            
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
   
    @Override
    public EstoqueProduto procurarProduto(int id) {
        // TODO Auto-generated method stub
        Connection conn = new DbConn().getConnection();
        
        try {
            String sql = "select nome,medida,preco,estoque from produto where id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            var resultQuery = ps.executeQuery();

            if (resultQuery.next()) {
                String nome = resultQuery.getString("nome");
                int medida = resultQuery.getInt("medida");
                conversaoMedida(medida);
                double preco = resultQuery.getDouble("preco");
                int estoque = resultQuery.getInt("estoque");
    
                produto = new EstoqueProduto(nome, medidaConvertida, preco, estoque);

                resultQuery.close();
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado com o ID: " + id);
            }
            ps.close();
            

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro ao conectar ao banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return produto;
        
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
    
}
