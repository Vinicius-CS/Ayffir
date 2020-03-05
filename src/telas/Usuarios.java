package telas;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.Cursor;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import conexao.ModuloConexao;
import net.proteanit.sql.DbUtils;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Usuarios extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField RegistrarNome;
	private JTextField RegistrarEMail;
	private JButton BtnRegistrar;
	private JButton BtnAlterar;
	private JButton BtnExcluir;
	private JTable TabelaUsuarios;
    private JTextField RegistrarSenha;
	
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
					Usuarios frame = new Usuarios();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void consultarauto(){
        String sql = "select ID, Nome, Loja, EMail, Senha, Cargo from usuarios where IDUser = ? order by ID desc";
        
        try {
            Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, inicio.getID());
            rs = pst.executeQuery();
            
            TabelaUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }
	
	private void registrar(JComboBox<?> RegistrarCargo){
        String sql = "insert into usuarios(Nome, Cargo, EMail, Senha, IDUser) values(?, ?, ?, ?, ?)";
        try {
        	Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, RegistrarNome.getText());
            pst.setString(2, (String) RegistrarCargo.getSelectedItem());
            pst.setString(3, RegistrarEMail.getText());
            pst.setString(4, RegistrarSenha.getText());
            pst.setString(5, inicio.getID());
            
            if((RegistrarNome.getText().isEmpty()) || (RegistrarEMail.getText().isEmpty()) || (RegistrarSenha.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }else{
            int adicionado = pst.executeUpdate();
            
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "O usuário foi registrado!");
                consultarauto();
                limparcampos();
                
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }
	
	private void alterar(JComboBox<?> RegistrarCargo){
        String sql = "update usuarios set Nome = ?, Cargo = ?, Senha = ? where EMail = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, RegistrarNome.getText());
            pst.setString(2, (String) RegistrarCargo.getSelectedItem());
            pst.setString(3, RegistrarSenha.getText());
            pst.setString(4, RegistrarEMail.getText());
            
            if((RegistrarNome.getText().isEmpty()) || (RegistrarEMail.getText().isEmpty()) || (RegistrarSenha.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }else{
            int adicionado = pst.executeUpdate();
            
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "O usuário foi alterado!");
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
            String sql = "delete from usuarios where EMail = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, RegistrarEMail.getText());
                int apagado = pst.executeUpdate();
            
            if(apagado > 0){
                JOptionPane.showMessageDialog(null, "O usuário foi apagado!");
                consultarauto();
                limparcampos();
                
            }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
            }
        }
    }
    
    public void setarcampos(){
        int setar = TabelaUsuarios.getSelectedRow();
        RegistrarNome.setText(TabelaUsuarios.getModel().getValueAt(setar, 1).toString());
        RegistrarEMail.setText(TabelaUsuarios.getModel().getValueAt(setar, 3).toString());
        RegistrarSenha.setText(TabelaUsuarios.getModel().getValueAt(setar, 4).toString());
    }
    
    private void limparcampos(){
            RegistrarNome.setText(null);
            RegistrarEMail.setText(null);
            RegistrarSenha.setText(null);
    }

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Usuarios() {
		setBounds(-5, -28, 805, 600);
		getContentPane().setLayout(null);
		
		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameOpened(InternalFrameEvent arg0) {
				consultarauto();
			}
		});
		
		conexao = ModuloConexao.conector();
		
		RegistrarNome = new JTextField();
		RegistrarNome.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarNome.setColumns(10);
		RegistrarNome.setBounds(100, 281, 287, 25);
		getContentPane().add(RegistrarNome);
		
		JComboBox RegistrarCargo = new JComboBox();
		RegistrarCargo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		RegistrarCargo.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Caixa"}));
		RegistrarCargo.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarCargo.setBounds(100, 315, 287, 25);
		getContentPane().add(RegistrarCargo);
		
		RegistrarEMail = new JTextField();
		RegistrarEMail.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarEMail.setColumns(10);
		RegistrarEMail.setBounds(100, 349, 287, 25);
		getContentPane().add(RegistrarEMail);
		
		RegistrarSenha = new JTextField();
		RegistrarSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarSenha.setColumns(10);
		RegistrarSenha.setBounds(100, 383, 287, 25);
		getContentPane().add(RegistrarSenha);
		
		BtnRegistrar = new JButton("");
		BtnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar(RegistrarCargo);
			}
		});
		BtnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BtnRegistrar.setIcon(new ImageIcon(Usuarios.class.getResource("/icones/Registrar - Ayffir.png")));
		BtnRegistrar.setBorderPainted(false);
		BtnRegistrar.setBorder(null);
		BtnRegistrar.setBounds(100, 419, 90, 25);
		getContentPane().add(BtnRegistrar);
		
		BtnAlterar = new JButton("");
		BtnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				alterar(RegistrarCargo);
			}
		});
		BtnAlterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BtnAlterar.setIcon(new ImageIcon(Usuarios.class.getResource("/icones/Alterar - Ayffir.png")));
		BtnAlterar.setBorderPainted(false);
		BtnAlterar.setBorder(null);
		BtnAlterar.setBounds(198, 419, 90, 25);
		getContentPane().add(BtnAlterar);
		
		BtnExcluir = new JButton("");
		BtnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		BtnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BtnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/icones/Excluir - Ayffir.png")));
		BtnExcluir.setBorderPainted(false);
		BtnExcluir.setBorder(null);
		BtnExcluir.setBounds(297, 419, 90, 25);
		getContentPane().add(BtnExcluir);
		
		TabelaUsuarios = new JTable();
		TabelaUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setarcampos();
			}
		});
		TabelaUsuarios.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
				{null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Nome", "Loja", "EMail", "Senha", "Cargo"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		TabelaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(25);
		TabelaUsuarios.setFont(new Font("Arial", Font.PLAIN, 12));
		TabelaUsuarios.setBorder(new LineBorder(new Color(0, 0, 0)));
		TabelaUsuarios.setBounds(411, 225, 368, 320);
		getContentPane().add(TabelaUsuarios);
		
		JLabel Fundo = new JLabel("");
		Fundo.setIcon(new ImageIcon(Usuarios.class.getResource("/icones/FundoUsuarios - Ayffir.png")));
		Fundo.setBounds(0, 0, 800, 571);
		getContentPane().add(Fundo);

	}
}
