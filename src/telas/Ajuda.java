package telas;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import conexao.ModuloConexao;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Ajuda extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField RegistrarTitulo;
	private JTable TabelaAjuda;
	private JTextField RegistrarDescricao;
    private JTextField RegistrarEMail;
    private JTextField RegistrarStatus;
	
	Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private JTextField RegistrarID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ajuda frame = new Ajuda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void consultarauto(){
        String sql = "select ID, Tipo, Urgencia, EMail, Titulo, Descricao, Status from reportes where IDUser = ? and Tipo = 'Suporte' order by ID desc";
        
        try {
            Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, inicio.getID());
            rs = pst.executeQuery();
            
            TabelaAjuda.setModel(DbUtils.resultSetToTableModel(rs));
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }
	
	private void registrar(JComboBox<?> RegistrarTipo, JComboBox<?> RegistrarUrgencia){
        String sql = "insert into reportes(Tipo, Urgencia, EMail, Titulo, Descricao, IDUser) values(?, ?, ?, ?, ?, ?)";
        try {
        	Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, (String) RegistrarTipo.getSelectedItem());
            pst.setString(2, (String) RegistrarUrgencia.getSelectedItem());
            pst.setString(3, RegistrarEMail.getText());
            pst.setString(4, RegistrarTitulo.getText());
            pst.setString(5, RegistrarDescricao.getText());
            pst.setString(6, inicio.getID());
            
            if((RegistrarEMail.getText().isEmpty()) || (RegistrarTitulo.getText().isEmpty()) || (RegistrarDescricao.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }else{
            int adicionado = pst.executeUpdate();
            
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "O pedido foi registrado!");
                consultarauto();
                limparcampos();
                
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }
	
	private void alterar(JComboBox<?> RegistrarTipo, JComboBox<?> RegistrarUrgencia){
        String sql = "update reportes set Tipo = ?, Urgencia = ?, Titulo = ?, Descricao = ? where IDUser = ? and EMail = ? and ID = ?";
        try {
            Inicio inicio = new Inicio();
            pst = conexao.prepareStatement(sql);
            pst.setString(1, (String) RegistrarTipo.getSelectedItem());
            pst.setString(2, (String) RegistrarUrgencia.getSelectedItem());
            pst.setString(3, RegistrarTitulo.getText());
            pst.setString(4, RegistrarDescricao.getText());
            pst.setString(5, inicio.getID());
            pst.setString(6, RegistrarEMail.getText());
            pst.setString(7, RegistrarID.getText());
            
            if((RegistrarEMail.getText().isEmpty()) || (RegistrarTitulo.getText().isEmpty()) || (RegistrarDescricao.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }else{
            int adicionado = pst.executeUpdate();
            
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "O pedido foi alterado!");
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
            String sql = "delete from reportes where IDUser = ? and EMail = ? and ID = ?";
            try {
            	Inicio inicio = new Inicio();
                pst = conexao.prepareStatement(sql);
                pst.setString(1, inicio.getID());
                pst.setString(2, RegistrarEMail.getText());
                pst.setString(3, RegistrarID.getText());
                int apagado = pst.executeUpdate();
            
            if(apagado > 0){
                JOptionPane.showMessageDialog(null, "O pedido foi apagado!");
                consultarauto();
                limparcampos();
                
            }
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
            }
        }
    }
    
    public void setarcampos(){
        int setar = TabelaAjuda.getSelectedRow();
        RegistrarID.setText(TabelaAjuda.getModel().getValueAt(setar, 0).toString());
        RegistrarEMail.setText(TabelaAjuda.getModel().getValueAt(setar, 3).toString());
        RegistrarTitulo.setText(TabelaAjuda.getModel().getValueAt(setar, 4).toString());
        RegistrarDescricao.setText(TabelaAjuda.getModel().getValueAt(setar, 5).toString());
        RegistrarStatus.setText(TabelaAjuda.getModel().getValueAt(setar, 6).toString());
    }
    
    private void limparcampos(){
    		Inicio inicio = new Inicio();
    		RegistrarEMail.setText(inicio.getEMAIL());
            RegistrarTitulo.setText(null);
            RegistrarDescricao.setText(null);
            RegistrarStatus.setText(null);
    }

	/**
	 * Create the frame.
	 */
	public Ajuda() {
		setBounds(-5, -28, 805, 600);
		getContentPane().setLayout(null);
		
		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameOpened(InternalFrameEvent arg0) {
				consultarauto();
				limparcampos();
			}
		});
		
		conexao = ModuloConexao.conector();
		
		JComboBox<Object> RegistrarTipo = new JComboBox<Object>();
		RegistrarTipo.setModel(new DefaultComboBoxModel<Object>(new String[] {"Suporte", "Sugest\u00E3o/Reclama\u00E7\u00E3o"}));
		RegistrarTipo.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarTipo.setBounds(145, 182, 634, 25);
		getContentPane().add(RegistrarTipo);
		
		JComboBox<Object> RegistrarUrgencia = new JComboBox<Object>();
		RegistrarUrgencia.setModel(new DefaultComboBoxModel<Object>(new String[] {"Baixa", "M\u00E9dia", "Alta"}));
		RegistrarUrgencia.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarUrgencia.setBounds(145, 216, 634, 25);
		getContentPane().add(RegistrarUrgencia);
		
		RegistrarEMail = new JTextField();
		RegistrarEMail.setEditable(false);
		RegistrarEMail.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarEMail.setColumns(10);
		RegistrarEMail.setBounds(145, 250, 634, 25);
		getContentPane().add(RegistrarEMail);
		
		RegistrarTitulo = new JTextField();
		RegistrarTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarTitulo.setColumns(10);
		RegistrarTitulo.setBounds(145, 283, 634, 25);
		getContentPane().add(RegistrarTitulo);
		
		RegistrarDescricao = new JTextField();
		RegistrarDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarDescricao.setColumns(10);
		RegistrarDescricao.setBounds(145, 318, 634, 72);
		getContentPane().add(RegistrarDescricao);
		
		JButton Registrar = new JButton("");
		Registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrar(RegistrarTipo, RegistrarUrgencia);
			}
		});
		
		RegistrarStatus = new JTextField();
		RegistrarStatus.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarStatus.setEditable(false);
		RegistrarStatus.setColumns(10);
		RegistrarStatus.setBounds(145, 398, 337, 25);
		getContentPane().add(RegistrarStatus);
		Registrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Registrar.setIcon(new ImageIcon(Ajuda.class.getResource("/icones/Registrar - Ayffir.png")));
		Registrar.setBorderPainted(false);
		Registrar.setBorder(null);
		Registrar.setBounds(492, 398, 90, 25);
		getContentPane().add(Registrar);
		
		JButton Alterar = new JButton("");
		Alterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar(RegistrarTipo, RegistrarUrgencia);
			}
		});
		Alterar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Alterar.setIcon(new ImageIcon(Ajuda.class.getResource("/icones/Alterar - Ayffir.png")));
		Alterar.setBorderPainted(false);
		Alterar.setBorder(null);
		Alterar.setBounds(590, 398, 90, 25);
		getContentPane().add(Alterar);
		
		JButton Excluir = new JButton("");
		Excluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		Excluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Excluir.setIcon(new ImageIcon(Ajuda.class.getResource("/icones/Excluir - Ayffir.png")));
		Excluir.setBorderPainted(false);
		Excluir.setBorder(null);
		Excluir.setBounds(689, 398, 90, 25);
		getContentPane().add(Excluir);
		
		TabelaAjuda = new JTable();
		TabelaAjuda.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setarcampos();
			}
		});
		TabelaAjuda.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Tipo", "Urgencia", "EMail", "Descricao", "Descricao", "Status"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		TabelaAjuda.getColumnModel().getColumn(0).setPreferredWidth(25);
		TabelaAjuda.setFont(new Font("Arial", Font.PLAIN, 12));
		TabelaAjuda.setBorder(new LineBorder(new Color(0, 0, 0)));
		TabelaAjuda.setBounds(10, 496, 769, 48);
		getContentPane().add(TabelaAjuda);
		
		JLabel Fundo = new JLabel("");
		Fundo.setIcon(new ImageIcon(Ajuda.class.getResource("/icones/FundoAjuda - Ayffir.png")));
		Fundo.setBounds(0, 0, 800, 571);
		getContentPane().add(Fundo);
		
		RegistrarID = new JTextField();
		RegistrarID.setVisible(false);
		RegistrarID.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistrarID.setEditable(false);
		RegistrarID.setColumns(10);
		RegistrarID.setBounds(0, 0, 53, 25);
		getContentPane().add(RegistrarID);

	}
}
