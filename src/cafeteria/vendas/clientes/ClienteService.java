package cafeteria.vendas.clientes;

import cafeteria.DbConn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class ClienteService implements IClienteService {
    private final Connection connection;

    Cliente cliente;

    public ClienteService() {
        this.connection = DbConn.getConnection(); 
    }

    @Override
    public void adicionarCliente(Cliente cliente) {
        try {
            String sql = "INSERT INTO cliente (id, nome, telefone) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1,cliente.getId());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getTelefone());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            //Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, null, e);
            JOptionPane.showMessageDialog(null, "Não pode Haver campos em Branco");
        }

    }

    @Override
    public void atualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, telefone = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();
            } catch (SQLException e) {
            Logger.getLogger(ClienteService.class.getName()).log(Level.SEVERE, "Erro ao atualizar cliente com ID: " + cliente.getId(), e);
        }
    
        
    }

    @Override
    public Cliente buscarCliente(int id) {
        String sql = "SELECT nome, telefone FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");
                cliente = new Cliente(nome, telefone);
                rs.close();
                
            }
            else System.out.println("Cliente não encontrado");

            stmt.close();
        } catch (SQLException e) {
        }

        return cliente;
    }

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id"), rs.getString("nome"), rs.getString("telefone")));
            }
        } catch (SQLException e) {
        }
        return clientes;
    }
}