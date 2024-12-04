package cafeteria.vendas.produtos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ProdutoView extends JInternalFrame {

	private static final String TITULO = "Cadastro de Produto";

	private static final int POSICAO_X_INICIAL = 60;
	private static final int POSICAO_Y_INICIAL = 60;

	private static final int LARGURA = 580;
	private static final int ALTURA = 300;

	private static final long serialVersionUID = 1L;

	private JTextField id = null;
	private JTextField nome;
	private JComboBox<UnidadeMedida> medida;
	private JFormattedTextField preco;
	private JTextField estoque;
	private JCheckBox temEstoque;

	private JButton btSalvar;
	private JButton btVoltar;
	private JButton btNovoProduto;
	private JButton btPesquisar;

	public String ultimaAcao = null;
	String nomeString;
	double precoDouble;
	int idInt;
	UnidadeMedida unidadeMedida;
	int estoqueInt = 0;
	EstoqueProduto produtoExistente;

	private IProdutoService service = null;

	/**
	 * Cria a janela do CRUD do produto
	 */
	public ProdutoView(IProdutoService service) {
		this.service = service;

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

		JLabel lbMedida = new JLabel("Medida:");
		lbMedida.setBounds(31, 106, 60, 17);
		getContentPane().add(lbMedida);

        medida = new JComboBox<>(UnidadeMedida.values());
		medida.setBounds(109, 104, 114, 21);
		getContentPane().add(medida);

		btSalvar = new JButton("Salvar");
		btSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickSalvar();
			}
		});
		btSalvar.setBounds(434, 229, 105, 27);
		getContentPane().add(btSalvar);

		btVoltar = new JButton("Voltar");
		btVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickVoltar();
			}
		});
		btVoltar.setBounds(322, 229, 105, 27);
		getContentPane().add(btVoltar);

		btNovoProduto = new JButton("Novo Produto");
		btNovoProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickIncluirNovoProduto();
				ultimaAcao = "Incluir";
			}
		});
		btNovoProduto.setBounds(400, 35, 139, 27);
		getContentPane().add(btNovoProduto);

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

		JLabel lbPreco = new JLabel("Preço:");
		lbPreco.setBounds(31, 136, 60, 17);
		getContentPane().add(lbPreco);

		JLabel lbEstoque = new JLabel("Estoque:");
		lbEstoque.setBounds(31, 205, 60, 17);
		getContentPane().add(lbEstoque);

		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		preco = new JFormattedTextField(numberFormat);
		preco.setHorizontalAlignment(SwingConstants.RIGHT);
		preco.setBounds(109, 137, 114, 21);
		getContentPane().add(preco);
		preco.setColumns(10);

		estoque = new JTextField();
		estoque.setHorizontalAlignment(SwingConstants.RIGHT);
		estoque.setBounds(109, 203, 114, 21);
		getContentPane().add(estoque);
		estoque.setColumns(10);

		temEstoque = new JCheckBox("Tem estoque?");
		temEstoque.setBounds(26, 166, 114, 25);
		getContentPane().add(temEstoque);
	}

	/**
	 * Prepara o frame para a ação de consultar
	 */
	public void setupConsultar() {
		// configura os botões de ação
		btSalvar.setEnabled(false);
		btVoltar.setEnabled(false);
		btNovoProduto.setEnabled(true);
		btPesquisar.setEnabled(true);

		// configura o comportamento dos campos
		id.setEnabled(true);
		nome.setEnabled(false);
		medida.setEnabled(false);
		preco.setEnabled(false);
		temEstoque.setEnabled(false);
		estoque.setEnabled(false);
	}
	ProdutoService produtoService = new ProdutoService();
	/**
	 * Executa as tarefas para efetuar uma pesquisa com base no ID informado
	 */
	protected void onClickPesquisar() {
    String idText = id.getText();
	
    try {
        int idInt = Integer.parseInt(idText);
        
        produtoExistente = produtoService.procurarProduto(idInt);
        
        if (produtoExistente != null) {
			id.setEnabled(false);
            nome.setEnabled(true);
            medida.setEnabled(true);
            preco.setEnabled(true);
            temEstoque.setEnabled(true);
            estoque.setEnabled(true);
			btSalvar.setEnabled(true);
			btVoltar.setEnabled(true);
			btNovoProduto.setEnabled(false);
			btPesquisar.setEnabled(false);


            nome.setText(produtoExistente.getNome());
            preco.setText(String.valueOf(produtoExistente.getPreco()));
			estoque.setText(String.valueOf(produtoExistente.getEstoque()));
			medida.setSelectedItem(produtoExistente.getMedida());

        } else {
            JOptionPane.showMessageDialog(null, "Produto não encontrado.");
			onClickVoltar();
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "ID inválido. Por favor, insira um número inteiro.");
    }
    
    System.out.println("==> onClickPesquisar");
}

	/**
	 * Executa as tarefas para preparar a interface para a inclusão de um novo
	 * produto
	 */
	protected void onClickIncluirNovoProduto() {

		nome.setEnabled(true);
		medida.setEnabled(true);
		preco.setEnabled(true);
		temEstoque.setEnabled(true);
		estoque.setEnabled(true);
		btNovoProduto.setEnabled(false);
		btPesquisar.setEnabled(false);
		btVoltar.setEnabled(true);
		btSalvar.setEnabled(true);

		System.out.println("==> onClickIncluirNovoProduto");

	}

	/**
	 * Executa as tarefas para voltar a inclusão de um produto
	 */
	protected void onClickVoltar() {

		// configura os botões de ação
		id.setText("");
		nome.setText("");
		preco.setValue(null);
		estoque.setText("");
		temEstoque.setSelected(false);

		btSalvar.setEnabled(false);
		btVoltar.setEnabled(false);
		btNovoProduto.setEnabled(true);
		btPesquisar.setEnabled(true);

		// configura o comportamento dos campos
		id.setEnabled(true);
		nome.setEnabled(false);
		medida.setEnabled(false);
		preco.setEnabled(false);
		temEstoque.setEnabled(false);
		estoque.setEnabled(false);
		System.out.println("==> onClickVoltar");
	}

	/**
	 * Executa as tarefas para salvar a inclusão de um novo produto
	 */
	protected void onClickSalvar() {

		switch (ultimaAcao){
			case "Incluir":{
		
				try{
					precoDouble = ((Number) preco.getValue()).doubleValue();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Preço inválido. Por favor, insira um número.");
					return;
				}

				try{
					idInt = Integer.parseInt(id.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Id Inválido. Por favor, insira um número.");
					return;
				}

				if (temEstoque.isSelected()){
					try{
						estoqueInt = Integer.parseInt(estoque.getText());
						if (estoqueInt == 0){
							JOptionPane.showMessageDialog(null, "Estoque inválido. Por favor, insira um número.");
                			return;
						}
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "Estoque inválido. Por favor, insira um número.");
                		return;
					}
				}
				nomeString = nome.getText();
				unidadeMedida = (UnidadeMedida) medida.getSelectedItem();
				int nomelenght = 1;
				System.out.println("nome é: " + nomeString);
				if(nomeString.length() >= nomelenght){
					EstoqueProduto novoProduto = new EstoqueProduto(nomeString, unidadeMedida, precoDouble, estoqueInt, idInt);
					produtoService.adicionarProduto(novoProduto);

					JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
				}else{
					JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
					return;
				}
				

				id.setText("");
				nome.setText("");
				preco.setValue(null);
				estoque.setText("");
				temEstoque.setSelected(false);

				onClickVoltar();
		
				System.out.println("==> onClickSalvar");
				break;
			}
			case "Pesquisar":{
				
				try{
					precoDouble = ((Number) preco.getValue()).doubleValue();
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Preço inválido. Por favor, insira um número.");
					return;
				}

				try{
					idInt = Integer.parseInt(id.getText());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null, "Id Inválido. Por favor, insira um número.");
					return;
				}

				if (temEstoque.isSelected()){
					try{
						estoqueInt = Integer.parseInt(estoque.getText());
						if (estoqueInt == 0){
							JOptionPane.showMessageDialog(null, "Estoque inválido. Por favor, insira um número.");
                			return;
						}
					}catch(Exception e){
						JOptionPane.showMessageDialog(null, "Estoque inválido. Por favor, insira um número.");
                		return;
					}
				}
				nomeString = nome.getText();
				unidadeMedida = (UnidadeMedida) medida.getSelectedItem();
				
				EstoqueProduto produtoAtualizado = new EstoqueProduto(nomeString, unidadeMedida, precoDouble, estoqueInt, idInt);
				if (nomeString != null){
					int resposta = JOptionPane.showConfirmDialog(null, "Cliente Já Existente. Deseja Atualizar?", "Atualizar Cliente", JOptionPane.YES_NO_OPTION);
					if(resposta == JOptionPane.YES_OPTION){
	
						produtoAtualizado.setNome(nomeString);
						produtoAtualizado.setMedida(unidadeMedida);
						produtoAtualizado.setPreco(precoDouble);
						produtoAtualizado.setEstoque(estoqueInt);
						produtoAtualizado.setId(idInt);

						produtoService.editarProduto(produtoAtualizado);

						JOptionPane.showMessageDialog(null, "Cliente Atualizado Com Sucesso!");
					}else{
						onClickVoltar();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Nome não pode estar em branco.");
					return;
				}
				
				
				onClickVoltar();
				break;
			}
			default: System.out.println("não deu né");
			break;


		}
	}
}
