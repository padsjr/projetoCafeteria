package cafeteria.relatorios;

import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import cafeteria.DbConn;

public class RelatorioProduto implements RelatorioExportavelEmArquivoTexto {

    @Override
    public String getNomeRelatorio() {
        return "Relatório de Produtos";
    }

    @Override
    public void exportar(File destino) {
        // SQL para obter todos os produtos
        String sql = "SELECT id, nome, medida, preco, estoque FROM produto ORDER BY nome";
        try (Connection conn = DbConn.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             FileWriter writer = new FileWriter(destino)) {

            // Escrever título
            writer.write("Relatório de Produtos\n");
            writer.write("=====================\n\n");

            // Escrever os dados
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String medida = rs.getString("medida");
                double preco = rs.getDouble("preco");
                int estoque = rs.getInt("estoque");

                writer.write(String.format("ID: %d\nNome: %s\nMedida: %s\nPreço: %.2f\nEstoque: %d\n\n",
                        id, nome, medida, preco, estoque));
            }

            System.out.println("Relatório de produtos gerado com sucesso!");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
