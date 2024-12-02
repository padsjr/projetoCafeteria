package cafeteria.vendas.clientes;

import java.util.List;

public interface IClienteService {

    void adicionarCliente(Cliente cliente);
    void atualizarCliente(Cliente cliente);
    Cliente buscarCliente(int id);
   List<Cliente> listarClientes();
    

}
    
