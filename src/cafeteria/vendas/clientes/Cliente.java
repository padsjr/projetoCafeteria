package cafeteria.vendas.clientes;

import javax.swing.JOptionPane;

public class Cliente {
    private int id;
    private String nome;
    private String telefone;

    
    public Cliente(int id, String nome, String telefone) {
        this(nome, telefone);
        this.id = id;
    
    }

    public Cliente(String nome, String telefone) {
        this.nome = nome;
        this.telefone = telefone; 
    }

    public int getId() {
        return id; 
    }

    public void setId(int id) {
        this.id = id; 
    }

    public String getNome() {
        return nome; 
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
            throw new IllegalArgumentException("Nome não pode ser vazio.");
            
        }
        this.nome = nome; 
    }

    public String getTelefone() {
        return telefone; 
    }

    public void setTelefone(String telefone) {
        if (telefone == null || telefone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Telefone não pode estar em branco.");
            throw new IllegalArgumentException("Telefone não pode ser vazio."); 
        }
        this.telefone = telefone; 
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}