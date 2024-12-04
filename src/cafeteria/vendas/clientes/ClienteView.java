package cafeteria.vendas.clientes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

public class ClienteView extends JInternalFrame {

	private static final String TITULO = "Cadastro de Cliente";

	private static final int POSICAO_X_INICIAL = 30;
	private static final int POSICAO_Y_INICIAL = 30;

	private static final int LARGURA = 580;
	private static final int ALTURA = 210;

	private static final long serialVersionUID = 1L;

	private JTextField id;
	private JTextField nome;
	private JFormattedTextField telefone;

	private final JButton btSalvar;
	private final JButton btVoltar;
	private final JButton btNovoCliente;
	private final JButton btPesquisar;

	public String ultimaAcao = null;
	int idInt;

	public ClienteService clienteService;
	private IClienteService service = null;

	/**
	 * Cria a janela do CRUD do cliente
	 */
        
	public ClienteView(IClienteService service) {
		this.service = service;
		this.clienteService = new ClienteService();

		setClosable(true);
		setIconifiable(true);
		setSize(LARGURA, ALTURA);
		setLocation(POSICAO_X_INICIAL, POSICAO_Y_INICIAL);
		setTitle(TITULO);
		getContentPane().setLayout(null);

		JLabel lbId = new JLabel("ID:");
		lbId.setBounds(31, 40, 60, 17);
		getContentPane().add(lbId);

		id = new JTextField();
		id.setHorizontalAlignment(SwingConstants.CENTER);
		id.setBounds(109, 38, 114, 21);
		getContentPane().add(id);
		id.setColumns(10);

		JLabel lbNome = new JLabel("Nome:");
		lbNome.setBounds(31, 73, 60, 17);
		getContentPane().add(lbNome);

		nome = new JTextField();
		nome.setBounds(109, 71, 430, 21);
		getContentPane().add(nome);
		nome.setColumns(10);

		JLabel lbTelefone = new JLabel("Telefone:");
		lbTelefone.setBounds(31, 106, 60, 17);
		getContentPane().add(lbTelefone);

		MaskFormatter maskFormatter;
		try {
			maskFormatter = new MaskFormatter("(##) #####-####");
			maskFormatter.setPlaceholderCharacter('_'); // Caracter de espaço reservado
			telefone = new JFormattedTextField(maskFormatter);
			telefone.setBounds(109, 104, 132, 21);
			getContentPane().add(telefone);
			telefone.setColumns(10);
		} catch (ParseException e) {
		}

		btSalvar = new JButton("Salvar");
		btSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickSalvar();
			}
		});
		btSalvar.setBounds(434, 126, 105, 27);
		getContentPane().add(btSalvar);

		btVoltar = new JButton("Voltar");
		btVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickVoltar();
			}
		});
		btVoltar.setBounds(317, 126, 105, 27);
		getContentPane().add(btVoltar);

		btNovoCliente = new JButton("Novo Cliente");
		btNovoCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickIncluirNovoCliente();
				ultimaAcao = "Incluir";
			}
		});
		btNovoCliente.setBounds(400, 35, 139, 27);
		getContentPane().add(btNovoCliente);

		btPesquisar = new JButton("Pesquisar");
		btPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickPesquisar();
				ultimaAcao = "Pesquisar";
			}
		});
		btPesquisar.setBounds(235, 35, 96, 27);
		getContentPane().add(btPesquisar);
	}

	/**
	 * Prepara o frame para a ação de consultar
	 */
	public void setupConsultar() {
		// configura os botões de ação
		btSalvar.setEnabled(false);
		btVoltar.setEnabled(false);
		btNovoCliente.setEnabled(true);
		btPesquisar.setEnabled(true);

		// configura o comportamento dos campos
		id.setEnabled(true);
		nome.setEnabled(false);
		telefone.setEnabled(false);
	}

	/**
	 * Executar as tarefas para efetuar uma pesquisa com base no ID informado
	 */
	protected void onClickPesquisar() {
		clienteService = new ClienteService();
		// 1) ler campo de tela ID
		String idString = this.id.getText();
		
		// 2) chamar o this.service.buscarCliente(id)
		int clienteId;
		try{
			clienteId = Integer.parseInt(idString);
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número.");
			return;
		}
		
		Cliente cliente = clienteService.buscarCliente(clienteId);
		
		// 3) ler o retorno do this.service
		if (cliente != null) {
			// 4) popular nos campos de tela com base no cliente
			this.nome.setText(cliente.getNome());
			this.telefone.setText(cliente.getTelefone());
			id.setEnabled(false);
			nome.setEnabled(true);
			telefone.setEnabled(true);
			// Habilitar os botões de ação
			btSalvar.setEnabled(true);
			btVoltar.setEnabled(true);
			btNovoCliente.setEnabled(false);
			btPesquisar.setEnabled(false);
		} else {
			JOptionPane.showMessageDialog(null,"Cliente não encontrado");
			onClickVoltar();

		}	
	}

	/**
	 * Executar as tarefas para preparar a interface para a inclusão de um novo
	 * cliente
	 */
	protected void onClickIncluirNovoCliente() {
		// Limpar os campos de entrada
		id.setText("");
		nome.setText("");
		telefone.setText("");

		// Habilitar os campos de entrada
		id.setEnabled(true);
		nome.setEnabled(true);
		telefone.setEnabled(true);

		// Configurar os botões de ação
		btSalvar.setEnabled(true);
		btVoltar.setEnabled(true);
		btNovoCliente.setEnabled(false);
		btPesquisar.setEnabled(false);

		java.util.logging.Logger.getLogger(ClienteView.class.getName()).log(java.util.logging.Level.INFO, "==> onClickIncluirNovoCliente");
	}
	/**
	 * Executar as tarefas para voltar a inclusão de um cliente
	 */
	protected void onClickVoltar() {
		// Limpar os campos de entrada
		id.setText("");
		nome.setText("");
		telefone.setText("");

		// Desabilitar os campos de entrada
		id.setEnabled(true);
		nome.setEnabled(false);
		telefone.setEnabled(false);

		// Configurar os botões de ação
		btSalvar.setEnabled(false);
		btVoltar.setEnabled(false);
		btNovoCliente.setEnabled(true);
		btPesquisar.setEnabled(true);

		java.util.logging.Logger.getLogger(ClienteView.class.getName()).log(java.util.logging.Level.INFO, "==> onClickVoltar");
	}

int tamanhoMaximo = 11;
int tamanhoMinimo = 10;
String unmaskedTelefone;

protected void onClickSalvar() {
	switch(ultimaAcao) {
		case "Incluir": {
			try {
				idInt = Integer.parseInt(this.id.getText());
			} catch (Exception e) { JOptionPane.showMessageDialog( null, "ID inválido" ); 
				return;
			}		
			
			String telefoneText = this.telefone.getText();
			unmaskedTelefone = telefoneText.replaceAll("[^0-9]", "");
			String nomeText = this.nome.getText();
			Cliente cliente;
			System.out.println(telefoneText);
			int nomeLenght = 1;
			if(nomeText.length() >= nomeLenght){

				if (unmaskedTelefone.length() > tamanhoMinimo && unmaskedTelefone.length() <= tamanhoMaximo){
					cliente = new Cliente(idInt, nomeText, telefoneText);
					clienteService.adicionarCliente(cliente);
					JOptionPane.showMessageDialog(null, "Cliente adicionado com sucesso");
					id.setText("");
					nome.setText("");
					telefone.setText("");
					onClickVoltar();
				}else{
					JOptionPane.showMessageDialog(null, "Telefone Inserido Não é válido");
					onClickIncluirNovoCliente();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
				return;
			}
			break;
		}
		case "Pesquisar": {
			// Ler os dados dos campos de entrada
		int idInt = Integer.parseInt(this.id.getText());
		String nomeText = this.nome.getText();
		String telefoneText = this.telefone.getText();

		// Verificar se o cliente já existe
		Cliente clienteExistente = new Cliente(idInt, nomeText, telefoneText);
	
		// Perguntar ao usuário se deseja atualizar o cliente existente
		int resposta = JOptionPane.showConfirmDialog(null, "Cliente já existe. Deseja atualizar?", "Atualizar Cliente", JOptionPane.YES_NO_OPTION);
		if (resposta == JOptionPane.YES_OPTION) {
			if(nomeText != null){
			unmaskedTelefone = telefoneText.replaceAll("[^0-9]", "");
			if(unmaskedTelefone.length() > tamanhoMinimo && unmaskedTelefone.length() <= tamanhoMaximo){
				clienteExistente.setId(idInt);
				clienteExistente.setNome(nomeText);
				clienteExistente.setTelefone(telefoneText);
				clienteService.atualizarCliente(clienteExistente);
				JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso");
				onClickVoltar();
			}else{
				JOptionPane.showMessageDialog(null, "Telefone Inserido Não é válido");
				onClickIncluirNovoCliente();
			}
		}else{
			JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
			onClickIncluirNovoCliente();
		}
		}else {
			onClickVoltar();
		}
		
	
			// Limpar os campos de entrada
			this.id.setText("");
			this.nome.setText("");
			this.telefone.setText("");
	
			// Configurar os botões de ação após salvar
			btSalvar.setEnabled(false);
			btVoltar.setEnabled(false);
			btNovoCliente.setEnabled(true);
			btPesquisar.setEnabled(true);
	
		
	
			java.util.logging.Logger.getLogger(ClienteView.class.getName()).log(java.util.logging.Level.INFO, "==> onClickSalvar");
			break;
		}
		default: System.out.println("Ação não reconhecida");
	}
	}
}
