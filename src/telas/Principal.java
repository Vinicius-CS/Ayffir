package telas;

import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import telas.Sobre;
import java.awt.Font;
import javax.swing.JMenu;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import conexao.ModuloConexao;
import net.proteanit.sql.DbUtils;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel PainelPrincipal;
	private JTextField NomeProduto;
	private JTextField CodigoProduto;
	private JTextField UnidadeProduto;
	private JTextField PrecoUnitario;
	private JTable Produtos;
	private JTable PesquisaProdutos;
	private JTextField PrecoTotalUnidade;
    private JTextField PrecoTotal;
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void consultar(){
        
        String sql = "select ID, Codigo, Nome, Preco from produtos where IDUser = ? and Nome like ? or Codigo like ?";
        
        try {
            Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, inicio.getID());
            pst.setString(2, NomeProduto.getText() + "%");
            pst.setString(3, CodigoProduto.getText() + "%");
            rs = pst.executeQuery();
            
            PesquisaProdutos.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }

	private void consultarauto(){
	    
	    String sql = "select ID, Codigo, Nome, Preco from produtos where IDUser = ? and Nome like ? or Codigo like ? order by ID desc";
	    
	    try {
	        Inicio inicio = new Inicio();
	        pst = conexao.prepareStatement(sql);
	        pst.setString(1, inicio.getID());
	        pst.setString(2, NomeProduto.getText() + "%");
	        pst.setString(3, CodigoProduto.getText() + "%");
	        rs = pst.executeQuery();
	        
	        PesquisaProdutos.setModel(DbUtils.resultSetToTableModel(rs));
	        
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
	    }
	}
	
	public void contatotal() {
		try {
	        double resultado = 0;
	        for (int i = 1; i < Produtos.getRowCount(); i++) {
	            resultado += Double.parseDouble(Produtos.getValueAt(i, 4).toString());
	            
	            String resultados = Double.toString(resultado);
	            PrecoTotal.setText(resultados);
	    }
	} catch (NumberFormatException e) {
	System.out.println("Erro ao somar: " +e.getMessage());
	}
	}
	
	public void conta() {
		int unidadeproduto = Integer.parseInt(UnidadeProduto.getText());
		double precounitario = Double.parseDouble(PrecoUnitario.getText());
		 
		double resultado = unidadeproduto * precounitario;
		String resultados = Double.toString(resultado);
		
		PrecoTotalUnidade.setText(resultados);
	}
	
	public void setarcampos(){
	    int setar = PesquisaProdutos.getSelectedRow();
	    CodigoProduto.setText(PesquisaProdutos.getModel().getValueAt(setar, 1).toString());
	    NomeProduto.setText(PesquisaProdutos.getModel().getValueAt(setar, 2).toString());
	    PrecoUnitario.setText(PesquisaProdutos.getModel().getValueAt(setar, 3).toString());
	}
	
	private void limparcampos(){
		NomeProduto.setText(null);
		CodigoProduto.setText(null);
		PrecoUnitario.setText("00.00");
		UnidadeProduto.setText("1");
	}
	
	private void adicionar() {
		DefaultTableModel dtm = (DefaultTableModel) Produtos.getModel();
		dtm.addRow(new Object[]{NomeProduto.getText(), CodigoProduto.getText(), UnidadeProduto.getText(), PrecoUnitario.getText(), PrecoTotalUnidade.getText()});
	}

	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				consultarauto();
			}
		});
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("/icones/LogoMenorAinda - Ayffir.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ayffir");
		setBounds(290, 60, 800, 600);
		PainelPrincipal = new JPanel();
		PainelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PainelPrincipal);
		PainelPrincipal.setLayout(null);
		
		conexao = ModuloConexao.conector();
		
		JDesktopPane Desktop = new JDesktopPane();
		Desktop.setToolTipText("");
		Desktop.setBackground(Color.WHITE);
		Desktop.setBounds(0, 21, 794, 600);
		PainelPrincipal.add(Desktop);
		
		NomeProduto = new JTextField();
		NomeProduto.setBounds(195, 103, 213, 25);
		Desktop.add(NomeProduto);
		NomeProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					consultar();
				}
			}
		});
		NomeProduto.setFont(new Font("Arial", Font.PLAIN, 12));
		NomeProduto.setColumns(10);
		
		CodigoProduto = new JTextField();
		CodigoProduto.setBounds(195, 136, 213, 25);
		Desktop.add(CodigoProduto);
		CodigoProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if(evt.getKeyCode() == KeyEvent.VK_ENTER){
					consultar();
				}
			}
		});
		CodigoProduto.setFont(new Font("Arial", Font.PLAIN, 12));
		CodigoProduto.setColumns(10);
		
		UnidadeProduto = new JTextField();
		UnidadeProduto.setBounds(195, 168, 213, 25);
		Desktop.add(UnidadeProduto);
		UnidadeProduto.setText("1");
		UnidadeProduto.setFont(new Font("Arial", Font.PLAIN, 12));
		UnidadeProduto.setColumns(10);
		
		PrecoUnitario = new JTextField();
		PrecoUnitario.setBounds(213, 200, 146, 25);
		Desktop.add(PrecoUnitario);
		PrecoUnitario.setText("00.00");
		PrecoUnitario.setEditable(false);
		PrecoUnitario.setFont(new Font("Arial", Font.PLAIN, 12));
		PrecoUnitario.setColumns(10);
		
		JLabel TextoMoeda = new JLabel("R$");
		TextoMoeda.setBounds(195, 204, 21, 14);
		Desktop.add(TextoMoeda);
		TextoMoeda.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton Add = new JButton("");
		Add.setBounds(369, 200, 39, 25);
		Desktop.add(Add);
		Add.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Add.setIcon(new ImageIcon(Caixa.class.getResource("/icones/Add - Ayffir.png")));
		Add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				conta();
				adicionar();
				consultarauto();
				limparcampos();
				contatotal();
			}
		});
		Add.setBorderPainted(false);
		Add.setBorder(null);
		
		PesquisaProdutos = new JTable();
		PesquisaProdutos.setBounds(10, 236, 398, 304);
		Desktop.add(PesquisaProdutos);
		PesquisaProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setarcampos();
			}
		});
		PesquisaProdutos.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
				{null, null, null, null},
			},
			new String[] {
				"ID", "Codigo", "Nome", "Preco"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		PesquisaProdutos.getColumnModel().getColumn(0).setPreferredWidth(25);
		PesquisaProdutos.setFont(new Font("Arial", Font.PLAIN, 12));
		PesquisaProdutos.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		Produtos = new JTable();
		Produtos.setBounds(432, 108, 347, 400);
		Desktop.add(Produtos);
		Produtos.setBorder(new LineBorder(new Color(0, 0, 0)));
		Produtos.setModel(new DefaultTableModel(
			new Object[][] {
				{"Código", "Nome", "Unidades", "Preço Unitário", "Preço Total"},
			},
			new String[] {
				"Codigo", "Nome", "Unidades", "PrecoUnitario", "PrecoTotal"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		Produtos.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JLabel TextoMoedaTotal = new JLabel("R$");
		TextoMoedaTotal.setBounds(590, 527, 21, 14);
		Desktop.add(TextoMoedaTotal);
		TextoMoedaTotal.setFont(new Font("Arial", Font.PLAIN, 12));
		
		PrecoTotal = new JTextField();
		PrecoTotal.setBounds(608, 522, 146, 25);
		Desktop.add(PrecoTotal);
		PrecoTotal.setText("00.00");
		PrecoTotal.setFont(new Font("Arial", Font.PLAIN, 12));
		PrecoTotal.setEditable(false);
		PrecoTotal.setColumns(10);
		
		JLabel Fundo = new JLabel("");
		Fundo.setBounds(0, 0, 800, 571);
		Desktop.add(Fundo);
		Fundo.setIcon(new ImageIcon(Caixa.class.getResource("/icones/FundoCaixa - Ayffir.png")));
		
		PrecoTotalUnidade = new JTextField();
		PrecoTotalUnidade.setBounds(0, 0, 39, 25);
		Desktop.add(PrecoTotalUnidade);
		PrecoTotalUnidade.setVisible(false);
		PrecoTotalUnidade.setText("00.00");
		PrecoTotalUnidade.setFont(new Font("Arial", Font.PLAIN, 12));
		PrecoTotalUnidade.setEditable(false);
		PrecoTotalUnidade.setColumns(10);
		
		JMenuBar Menu = new JMenuBar();
		Menu.setBackground(Color.WHITE);
		Menu.setFont(new Font("Arial", Font.PLAIN, 12));
		Menu.setBounds(0, 0, 794, 21);
		PainelPrincipal.add(Menu);
		
		JMenu MenuPCaixa = new JMenu("Caixa");
		Menu.add(MenuPCaixa);
		MenuPCaixa.setForeground(Color.DARK_GRAY);
		MenuPCaixa.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JMenuItem MenuCaixa = new JMenuItem("Abrir Caixa");
		MenuCaixa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Caixa caixa = new Caixa();
		        Desktop.add(caixa);
		        caixa.setVisible(true);
			}
		});
		MenuCaixa.setForeground(Color.DARK_GRAY);
		MenuCaixa.setFont(new Font("Arial", Font.PLAIN, 12));
		MenuPCaixa.add(MenuCaixa);
		
		JMenu MenuPInformacoes = new JMenu("Informa\u00E7\u00F5es");
		MenuPInformacoes.setForeground(Color.DARK_GRAY);
		MenuPInformacoes.setFont(new Font("Arial", Font.PLAIN, 12));
		Menu.add(MenuPInformacoes);
		
		JMenuItem MenuProdutos = new JMenuItem("Produtos");
		MenuProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produtos produtos = new Produtos();
		        Desktop.add(produtos);
		        produtos.setVisible(true);
			}
		});
		MenuProdutos.setFont(new Font("Arial", Font.PLAIN, 12));
		MenuProdutos.setForeground(Color.DARK_GRAY);
		MenuPInformacoes.add(MenuProdutos);
		
		JMenuItem MenuCadastroUusarios = new JMenuItem("Cadastro de Usu\u00E1rios");
		MenuCadastroUusarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
		        Desktop.add(usuarios);
		        usuarios.setVisible(true);
			}
		});
		MenuCadastroUusarios.setForeground(Color.DARK_GRAY);
		MenuCadastroUusarios.setFont(new Font("Arial", Font.PLAIN, 12));
		MenuPInformacoes.add(MenuCadastroUusarios);
		
		JMenu MenuPOutros = new JMenu("Outros");
		MenuPOutros.setForeground(Color.DARK_GRAY);
		MenuPOutros.setFont(new Font("Arial", Font.PLAIN, 12));
		Menu.add(MenuPOutros);
		
		JMenuItem MenuAjuda = new JMenuItem("Ajuda");
		MenuAjuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Ajuda ajuda = new Ajuda();
		        Desktop.add(ajuda);
		        ajuda.setVisible(true);
			}
		});
		MenuPOutros.add(MenuAjuda);
		MenuAjuda.setForeground(Color.DARK_GRAY);
		MenuAjuda.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JMenuItem MenuSobre = new JMenuItem("Sobre");
		MenuSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sobre sobre = new Sobre();
		        Desktop.add(sobre);
		        sobre.setVisible(true);
			}
		});
		MenuSobre.setForeground(Color.DARK_GRAY);
		MenuSobre.setFont(new Font("Arial", Font.PLAIN, 12));
		MenuPOutros.add(MenuSobre);
	}
}
