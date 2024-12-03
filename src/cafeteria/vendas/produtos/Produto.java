package cafeteria.vendas.produtos;

import javax.swing.JOptionPane;

public class Produto {
    
    int id;
    String nome;
    UnidadeMedida medida;
    double preco;

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public UnidadeMedida getMedida() {
        return medida;
    }
    public double getPreco() {
        return preco;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        this.nome = nome;
    }
    public void setMedida(UnidadeMedida medida) {
        this.medida = medida;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
}
