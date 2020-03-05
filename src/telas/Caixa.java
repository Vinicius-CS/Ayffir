package telas;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import conexao.ModuloConexao;
import net.proteanit.sql.DbUtils;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class Caixa extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Caixa frame = new Caixa();
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

	/**
	 * Create the frame.
	 */
	public Caixa() {
		addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
				consultarauto();
			}
		});
		setBounds(-5, -28, 805, 600);
		getContentPane().setLayout(null);
		
		conexao = ModuloConexao.conector();
		
		NomeProduto = new JTextField();
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
		NomeProduto.setBounds(195, 103, 213, 25);
		getContentPane().add(NomeProduto);
		
		CodigoProduto = new JTextField();
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
		CodigoProduto.setBounds(195, 136, 213, 25);
		getContentPane().add(CodigoProduto);
		
		UnidadeProduto = new JTextField();
		UnidadeProduto.setText("1");
		UnidadeProduto.setFont(new Font("Arial", Font.PLAIN, 12));
		UnidadeProduto.setColumns(10);
		UnidadeProduto.setBounds(195, 168, 213, 25);
		getContentPane().add(UnidadeProduto);
		
		PrecoUnitario = new JTextField();
		PrecoUnitario.setText("00.00");
		PrecoUnitario.setEditable(false);
		PrecoUnitario.setFont(new Font("Arial", Font.PLAIN, 12));
		PrecoUnitario.setColumns(10);
		PrecoUnitario.setBounds(213, 200, 146, 25);
		getContentPane().add(PrecoUnitario);
		
		JLabel TextoMoeda = new JLabel("R$");
		TextoMoeda.setFont(new Font("Arial", Font.PLAIN, 12));
		TextoMoeda.setBounds(195, 204, 21, 14);
		getContentPane().add(TextoMoeda);
		
		Produtos = new JTable();
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
		
		PesquisaProdutos = new JTable();
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
		
		JButton Add = new JButton("");
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
		Add.setBounds(369, 200, 39, 25);
		getContentPane().add(Add);
		PesquisaProdutos.setFont(new Font("Arial", Font.PLAIN, 12));
		PesquisaProdutos.setBorder(new LineBorder(new Color(0, 0, 0)));
		PesquisaProdutos.setBounds(10, 236, 398, 304);
		getContentPane().add(PesquisaProdutos);
		Produtos.setFont(new Font("Arial", Font.PLAIN, 12));
		Produtos.setBounds(432, 108, 347, 400);
		getContentPane().add(Produtos);
		
		JLabel TextoMoedaTotal = new JLabel("R$");
		TextoMoedaTotal.setFont(new Font("Arial", Font.PLAIN, 12));
		TextoMoedaTotal.setBounds(590, 527, 21, 14);
		getContentPane().add(TextoMoedaTotal);
		
		PrecoTotal = new JTextField();
		PrecoTotal.setText("00.00");
		PrecoTotal.setFont(new Font("Arial", Font.PLAIN, 12));
		PrecoTotal.setEditable(false);
		PrecoTotal.setColumns(10);
		PrecoTotal.setBounds(608, 522, 146, 25);
		getContentPane().add(PrecoTotal);
		
		JLabel Fundo = new JLabel("");
		Fundo.setIcon(new ImageIcon(Caixa.class.getResource("/icones/FundoCaixa - Ayffir.png")));
		Fundo.setBounds(0, 0, 800, 571);
		getContentPane().add(Fundo);
		
		PrecoTotalUnidade = new JTextField();
		PrecoTotalUnidade.setVisible(false);
		PrecoTotalUnidade.setText("00.00");
		PrecoTotalUnidade.setFont(new Font("Arial", Font.PLAIN, 12));
		PrecoTotalUnidade.setEditable(false);
		PrecoTotalUnidade.setColumns(10);
		PrecoTotalUnidade.setBounds(0, 0, 39, 25);
		getContentPane().add(PrecoTotalUnidade);

	}
}
