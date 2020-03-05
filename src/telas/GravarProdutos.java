package telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GravarProdutos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static BufferedReader reader;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
	        // Conteúdo
	        String content =
	        		"\nID = 1\n"
	        		+ "Código = 12345678\n"
	        		+ "Nome = Bolacha\n"
	        		+ "Valor = 30.00\n";

	        // Cria arquivo
	        File file = new File("Produtos.txt");

	        // Se o arquivo não existir, ele gera
	        if (!file.exists()) {
	            file.createNewFile();
	        }

	        // Prepara para escrever no arquivo
	        FileWriter fw = new FileWriter(file.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	        
	        // Escreve e fecha o arquivo
	        bw.write(content);
	        bw.close();
	        
	        // Lê o arquivo
	        FileReader ler = new FileReader("Produtos.txt");
	        reader = new BufferedReader(ler);  
	        String linha;
	        while( (linha = reader.readLine()) != null ){
	            System.out.println(linha);
	        }

	        // Imprime confirmacao
	        System.out.println("\nGravado!");

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GravarProdutos frame = new GravarProdutos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GravarProdutos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
