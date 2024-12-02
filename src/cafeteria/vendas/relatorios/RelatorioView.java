package cafeteria.vendas.relatorios;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RelatorioView extends JInternalFrame {

	private static final int POSICAO_X_INICIAL = 90;
	private static final int POSICAO_Y_INICIAL = 90;

	private static final int LARGURA = 580;
	private static final int ALTURA = 190;

	private static final long serialVersionUID = 1L;

	private JTextField nomeRelatorio;
	private JTextField destinoCaminhoAbsoluto;
	private File destinoSelecionado = null;

	private JButton btExportar;
	private JButton btCancelar;
	private JButton btSelecionarDestino;

	private RelatorioExportavelEmArquivoTexto exportador = null;

	/**
	 * Cria a janela para exportação do relatório
	 */
	public RelatorioView(RelatorioExportavelEmArquivoTexto exportador) {
		this.exportador = exportador;

		setClosable(true);
		setIconifiable(true);
		setSize(LARGURA, ALTURA);
		setLocation(POSICAO_X_INICIAL, POSICAO_Y_INICIAL);
		setTitle("Exportador de Relatório");
		getContentPane().setLayout(null);

		JLabel lbId = new JLabel("Relatório:");
		lbId.setBounds(31, 40, 60, 17);
		getContentPane().add(lbId);

		nomeRelatorio = new JTextField();
		nomeRelatorio.setBounds(109, 38, 430, 21);
		getContentPane().add(nomeRelatorio);
		nomeRelatorio.setColumns(10);
		nomeRelatorio.setEditable(false);
		// TODO: Descomentar a linha abaixo quando já existir um exportador criado na TelaPricipal
		nomeRelatorio.setText(exportador.getNomeRelatorio());

		JLabel lbDestino = new JLabel("Destino:");
		lbDestino.setBounds(31, 73, 60, 17);
		getContentPane().add(lbDestino);

		destinoCaminhoAbsoluto = new JTextField();
		destinoCaminhoAbsoluto.setBounds(109, 71, 266, 21);
		getContentPane().add(destinoCaminhoAbsoluto);
		destinoCaminhoAbsoluto.setColumns(10);
		destinoCaminhoAbsoluto.setEditable(false);

		btExportar = new JButton("Exportar");
		btExportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickExportar();
			}
		});
		btExportar.setBounds(434, 107, 105, 27);
		getContentPane().add(btExportar);
		btExportar.setEnabled(false);

		btCancelar = new JButton("Cancelar");
		btCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickCancelar();
			}
		});
		btCancelar.setBounds(316, 107, 105, 27);
		getContentPane().add(btCancelar);
		btCancelar.setEnabled(true);

		btSelecionarDestino = new JButton("Selecionar Destino");
		btSelecionarDestino.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onClickSelecionarDestino();
			}
		});
		btSelecionarDestino.setBounds(387, 68, 152, 27);
		getContentPane().add(btSelecionarDestino);
		btSelecionarDestino.setEnabled(true);
	}

	/**
	 * Executa as tarefas para efetuar uma pesquisa com base no ID informado
	 */
	protected void onClickSelecionarDestino() {
		// Criação do JFileChooser
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			// Obtendo o arquivo selecionado
			destinoSelecionado = fileChooser.getSelectedFile();
			destinoCaminhoAbsoluto.setText(destinoSelecionado.getAbsolutePath());
			btExportar.setEnabled(true);
		}
	}

	/**
	 * Executa as tarefas para cancelar a geração do relatório
	 */
	protected void onClickCancelar() {
		this.dispose();
	}

	/**
	 * Executa as tarefas para exportar a exportação do relatório
	 */
	protected void onClickExportar() {
		// TODO: Implementar
	if (destinoSelecionado == null) {
			System.err.println("Destino não selecionado.");
			return;
		}

		// Verifica se o destino é um diretório
		if (destinoSelecionado.isDirectory()) {
			// Adiciona o nome do relatório ao caminho do diretório
			String nomeArquivo = nomeRelatorio.getText().replaceAll("[\\\\/:*?\"<>|]", "_") + ".txt";
			String caminhoDestino = destinoSelecionado.getAbsolutePath() + File.separator + nomeArquivo;
			destinoSelecionado = new File(caminhoDestino);

			// Verifica se o diretório existe, se não, cria
			File diretórioDestino = new File(destinoSelecionado.getParent());
			if (!diretórioDestino.exists()) {
				if (!diretórioDestino.mkdirs()) {
					System.err.println("Erro ao criar o diretório no destino.");
					return;
				}
			}

			// Verifica se o arquivo existe, se não, cria
			if (!destinoSelecionado.exists()) {
				try {
					boolean criado = destinoSelecionado.createNewFile(); // Tenta criar o arquivo
					if (!criado) {
						System.err.println("Erro ao criar o arquivo no destino.");
						return;
					}
				} catch (IOException e) {
					System.err.println("Erro ao tentar criar o arquivo: " + e.getMessage());
					e.printStackTrace();
					return;
				}
			}
		}

		// Verifica se o destino (agora garantido ser um arquivo) pode ser escrito
		//if (!destinoSelecionado.canWrite()) {
		//	System.err.println("Destino inválido ou não selecionado.AQUI");
		//	return;
		//}

		try {
			// Verificando se o nome do relatório está preenchido
			if (nomeRelatorio.getText().isEmpty()) {
				System.err.println("Nome do relatório não está preenchido.");
				return;
			}

			// Chamada ao exportador para gerar o relatório
			exportador.exportar(destinoSelecionado);
			System.out.println("Relatório exportado com sucesso para: " + destinoSelecionado.getAbsolutePath());

			// Opcional: Mensagem de sucesso para o usuário
			javax.swing.JOptionPane.showMessageDialog(this,
					"Relatório exportado com sucesso!",
					"Sucesso",
					javax.swing.JOptionPane.INFORMATION_MESSAGE);

		} catch (Exception ex) {
			// Tratamento de erros
			System.err.println("Erro ao exportar o relatório: " + ex.getMessage());
			ex.printStackTrace();

			// Opcional: Mensagem de erro para o usuário
			javax.swing.JOptionPane.showMessageDialog(this,
					"Erro ao exportar o relatório: " + ex.getMessage(),
					"Erro",
					javax.swing.JOptionPane.ERROR_MESSAGE);
		}
	}
}
