package cafeteria.vendas.relatorios;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import cafeteria.DbConn;

public class RelatorioClientes implements RelatorioExportavelEmArquivoTexto {

    @Override
    public String getNomeRelatorio() {
        return "Relatório de Clientes";
    }

    @Override
    public void exportar(File destino) {
        // SQL para obter todos os clientes
        String sql = "SELECT id, nome, telefone FROM cliente ORDER BY nome";
        try (Connection conn = DbConn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter writer = new FileWriter(destino)) {

            // Escrever título
            writer.write("Relatório de Clientes\n");
            writer.write("=====================\n\n");

            // Escrever os dados
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String telefone = rs.getString("telefone");

                writer.write(String.format("ID: %d\nNome: %s\nTelefone: %s\n\n", id, nome, telefone));
            }

            System.out.println("Relatório de clientes gerado com sucesso!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
