package cafeteria.relatorios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RelatorioVendas implements RelatorioExportavelEmArquivoTexto{
    @Override
    public String getNomeRelatorio() {
        return "Relatório de Vendas";//corrigir
    }

    @Override
    public void exportar(File destino) {
        try (FileWriter writer = new FileWriter(destino)) {
            writer.write("Conteúdo do Relatório de Vendas...");
            System.out.println("Relatório exportado para: " + destino.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao exportar o relatório", e);
        }
    }
}
