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
import net.proteanit.sql.DbUtils;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import conexao.ModuloConexao;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Produtos extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField RegistrarNome;
	private JTextField RegistrarPreco;
	private JTextField RegistrarQtdPrateleira;
	private JTextField RegistrarQtdEstoque;
	private JTable TabelaProdutos;
	private JTextField UserID;
	private JTextField RegistrarCodigo;
    private JTextField RegistrarQtdVendido;
	
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
					Produtos frame = new Produtos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void consultarauto(){
        String sql = "select ID, Codigo, Nome, Preco, Vendido, Prateleira, Estoque from produtos where IDUser = ? order by ID desc";
        
        try {
            Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, inicio.getID());
            rs = pst.executeQuery();
            
            TabelaProdutos.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }
	
	private void registrar(){
        String sql = "insert into produtos(Codigo, Nome, Preco, Prateleira, Estoque, IDUser) values (?, ?, ?, ?, ?, ?)";
        try {
        	Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, RegistrarCodigo.getText());
            pst.setString(2, RegistrarNome.getText());
            pst.setString(3, RegistrarPreco.getText());
            pst.setString(4, RegistrarQtdPrateleira.getText());
            pst.setString(5, RegistrarQtdEstoque.getText());
            pst.setString(6, inicio.getID());
            
            if((RegistrarCodigo.getText().isEmpty()) || (RegistrarNome.getText().isEmpty()) || (RegistrarPreco.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }else{
           limparcampos();
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "O produto foi registrado!");
                consultarauto();
                limparcampos();
                
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }

	private void alterar(){
        String sql = "update produtos set Nome = ?, Preco = ?, Vendido = ?, Prateleira = ?, Estoque = ? where Codigo = ? and IDUser = ?";
        try {
            Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, RegistrarNome.getText());
            pst.setString(2, RegistrarPreco.getText());
            pst.setString(3, RegistrarQtdVendido.getText());
            pst.setString(4, RegistrarQtdPrateleira.getText());
            pst.setString(5, RegistrarQtdEstoque.getText());
            pst.setString(6, RegistrarCodigo.getText());
            pst.setString(7, inicio.getID());
            
            if((RegistrarCodigo.getText().isEmpty()) || (RegistrarNome.getText().isEmpty()) || (RegistrarPreco.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }else{
            
            limparcampos();
            int adicionado = pst.executeUpdate();
            
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "O produto foi alterado!");
                consultarauto();
                limparcampos();
                
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        
        }
    }
    
	private void excluir(){
        int confirmar = JOptionPane.showConfirmDialog(null, "Deseja apagar?", "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION){
            String sql = "delete from produtos where Codigo = ? and IDUser = ?";
            try {
            	Inicio inicio = new Inicio();
                pst = conexao.prepareStatement(sql);
                pst.setString(1, RegistrarCodigo.getText());
                pst.setString(2, inicio.getID());
                int apagado = pst.executeUpdate();
            
            if(apagado > 0){
                JOptionPane.showMessageDialog(null, "O produto foi apagado!");
                consultarauto();
                limparcampos();
                
            }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
            }
        }
    }
    
    public void setarcampos(){
        int setar = TabelaProdutos.getSelectedRow();
        RegistrarCodigo.setText(TabelaProdutos.getModel().getValueAt(setar, 1).toString());
        RegistrarNome.setText(TabelaProdutos.getModel().getValueAt(setar, 2).toString());
        RegistrarPreco.setText(TabelaProdutos.getModel().getValueAt(setar, 3).toString());
        RegistrarQtdVendido.setText(TabelaProdutos.getModel().getValueAt(setar, 4).toString());
        RegistrarQtdPrateleira.setText(TabelaProdutos.getModel().getValueAt(setar, 5).toString());
        RegistrarQtdEstoque.setText(TabelaProdutos.getModel().getValueAt(setar, 6).toString());
    }
    
    private void limparcampos(){
    	RegistrarCodigo.setText(null);
    	RegistrarNome.setText(null);
    	RegistrarPreco.setText("00.00");
    	RegistrarQtdVendido.setText("0");
    	RegistrarQtdPrateleira.setText("0");
    	RegistrarQtdEstoque.setText("0");
    }

	/**
	 * Create the frame.
	 */
	public Produtos() {
		setBounds(-5, -28, 805, 600);
		getContentPane().setLayout(null);
		
		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameOpened(InternalFrameEvent arg0) {
				consultarauto();
			}
		});
		
		conexao = ModuloConexao.conector();
		
		RegistrarCodigo = new JTextField();
		RegistrarCodigo.setToolTipText("");
		RegistrarCodigo.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarCodigo.setColumns(10);
		RegistrarCodigo.setBounds(211, 192, 233, 25);
		getContentPane().add(RegistrarCodigo);
		
		RegistrarNome = new JTextField();
		RegistrarNome.setToolTipText("");
		RegistrarNome.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarNome.setColumns(10);
		RegistrarNome.setBounds(211, 228, 233, 25);
		getContentPane().add(RegistrarNome);
		
		RegistrarPreco = new JTextField();
		RegistrarPreco.setText("00.00");
		RegistrarPreco.setToolTipText("");
		RegistrarPreco.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarPreco.setColumns(10);
		RegistrarPreco.setBounds(231, 260, 213, 25);
		getContentPane().add(RegistrarPreco);
		
		RegistrarQtdPrateleira = new JTextField();
		RegistrarQtdPrateleira.setText("0");
		RegistrarQtdPrateleira.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarQtdPrateleira.setColumns(10);
		RegistrarQtdPrateleira.setBounds(211, 327, 233, 25);
		getContentPane().add(RegistrarQtdPrateleira);
		
		RegistrarQtdEstoque = new JTextField();
		RegistrarQtdEstoque.setText("0");
		RegistrarQtdEstoque.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarQtdEstoque.setColumns(10);
		RegistrarQtdEstoque.setBounds(211, 362, 233, 25);
		getContentPane().add(RegistrarQtdEstoque);
		
		RegistrarQtdVendido = new JTextField();
		RegistrarQtdVendido.setEditable(false);
		RegistrarQtdVendido.setText("0");
		RegistrarQtdVendido.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarQtdVendido.setColumns(10);
		RegistrarQtdVendido.setBounds(211, 294, 233, 25);
		getContentPane().add(RegistrarQtdVendido);
		
		JLabel TextoMoeda = new JLabel("R$");
		TextoMoeda.setFont(new Font("Arial", Font.PLAIN, 12));
		TextoMoeda.setBounds(212, 265, 21, 14);
		getContentPane().add(TextoMoeda);
		
		TabelaProdutos = new JTable();
		TabelaProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					setarcampos();
			}
		});
		TabelaProdutos.setBorder(new LineBorder(new Color(0, 0, 0)));
		TabelaProdutos.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "C\u00F3digo", "Nome", "Pre\u00E7o", "Qtd. Vendido", "Qtd. Prateleira", "Qtd. Estoque"
			}
		));
		TabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(25);
		TabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(90);
		TabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(50);
		TabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(73);
		TabelaProdutos.getColumnModel().getColumn(5).setPreferredWidth(81);
		TabelaProdutos.getColumnModel().getColumn(6).setPreferredWidth(74);
		TabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 12));
		TabelaProdutos.setBounds(466, 230, 313, 320);
		getContentPane().add(TabelaProdutos);
		
		JButton Registrar = new JButton("");
		Registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar();
			}
		});
		Registrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Registrar.setIcon(new ImageIcon(Produtos.class.getResource("/icones/Registrar - Ayffir.png")));
		Registrar.setBorderPainted(false);
		Registrar.setBorder(null);
		Registrar.setBounds(86, 420, 90, 25);
		getContentPane().add(Registrar);
		
		JButton Alterar = new JButton("");
		Alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		Alterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Alterar.setIcon(new ImageIcon(Produtos.class.getResource("/icones/Alterar - Ayffir.png")));
		Alterar.setBorderPainted(false);
		Alterar.setBorder(null);
		Alterar.setBounds(183, 420, 90, 25);
		getContentPane().add(Alterar);
		
		JButton Excluir = new JButton("");
		Excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		Excluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Excluir.setIcon(new ImageIcon(Produtos.class.getResource("/icones/Excluir - Ayffir.png")));
		Excluir.setBorderPainted(false);
		Excluir.setBorder(null);
		Excluir.setBounds(280, 420, 90, 25);
		getContentPane().add(Excluir);
		
		JLabel Fundo = new JLabel("");
		Fundo.setIcon(new ImageIcon(Produtos.class.getResource("/icones/FundoProduto - Ayffir.png")));
		Fundo.setBounds(0, 0, 800, 571);
		getContentPane().add(Fundo);
		
		UserID = new JTextField();
		UserID.setVisible(false);
		UserID.setBounds(0, 0, 20, 20);
		getContentPane().add(UserID);
		UserID.setColumns(10);

	}
}
