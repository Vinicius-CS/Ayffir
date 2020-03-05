package telas;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import telas.Inicio;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import conexao.ModuloConexao;
import java.awt.Font;
import java.awt.Color;

public class Inicio extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel PainelInicio;
	private JTextField LoginEMail;
	private JPasswordField LoginSenha;
	private JTextField RegistroLoja;
	private JTextField RegistroNome;
	private JTextField RegistroEMail;
	private JPasswordField RegistroSenha;
	
	Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private static String ID;
    private static String EMAIL;
    private JLabel TextoVersaoAtual;
	
    public String getID() {
        return ID;
    }
    
    public void setID(String ID){
        Inicio.ID = ID;
    }
    
    public String getEMAIL() {
        return EMAIL;
    }
    
    public void setEMAIL(String EMAIL){
    	Inicio.EMAIL = EMAIL;
    }
    
	@SuppressWarnings("deprecation")
	public void Logar() {
        String sql = "select * from usuarios where EMail = ? and Senha = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, LoginEMail.getText());
            pst.setString(2, LoginSenha.getText());
            
            rs = pst.executeQuery();
            
            if(rs.next()){
                String cargo = rs.getString(6);
                if(cargo.equals("Administrador")){
                Principal principal = new Principal();
                principal.setVisible(true);
                this.setID(rs.getString("ID"));
                this.setEMAIL(rs.getString("EMail"));
                this.dispose();
                }else{
                PrincipalCaixa principalcaixa = new PrincipalCaixa();
                principalcaixa.setVisible(true);
                this.setID(rs.getString("ID"));
                this.setEMAIL(rs.getString("EMail"));
                this.dispose();
                //Date data = new Date(System.currentTimeMillis());
                //SimpleDateFormat formatarDate = new SimpleDateFormat("dd-MM-yyyy");
                }
                
            }else{
                JOptionPane.showMessageDialog(null, "E-mail ou senha inválido!");
            }
            
        } catch (Exception e) {
               JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }
	
	@SuppressWarnings("deprecation")
	private void registrar(){
        String sql = "insert into usuarios(Nome, Loja, EMail, Senha) values(?, ?, ?, ?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, RegistroNome.getText());
            pst.setString(2, RegistroLoja.getText());
            pst.setString(3, RegistroEMail.getText());
            pst.setString(4, RegistroSenha.getText());
            
            if((RegistroNome.getText().isEmpty()) || (RegistroLoja.getText().isEmpty()) || (RegistroEMail.getText().isEmpty()) || (RegistroSenha.getText().isEmpty())){
                JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
            }else{
            limparCampos();
            int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "Registrado com sucesso!");
                
            }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Foi encontrado um erro:\n" + e);
        }
    }
    
    private void limparCampos(){
            RegistroNome.setText(null);
            RegistroLoja.setText(null);
            RegistroEMail.setText(null);
            RegistroSenha.setText(null);
            LoginEMail.setText(null);
            LoginSenha.setText(null);
    }

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Inicio frame = new Inicio();
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
	public Inicio() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Inicio.class.getResource("/icones/LogoMenorAinda - Ayffir.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Ayffir - Inicio");
		setBounds(290, 60, 800, 600);
		PainelInicio = new JPanel();
		PainelInicio.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PainelInicio);
		PainelInicio.setLayout(null);
		
		LoginEMail = new JTextField();
		LoginEMail.setFont(new Font("Arial", Font.PLAIN, 12));
		LoginEMail.setBounds(96, 313, 295, 25);
		PainelInicio.add(LoginEMail);
		LoginEMail.setColumns(10);
		
		LoginSenha = new JPasswordField();
		LoginSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		LoginSenha.setBounds(96, 348, 295, 25);
		PainelInicio.add(LoginSenha);
		
		RegistroNome = new JTextField();
		RegistroNome.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistroNome.setColumns(10);
		RegistroNome.setBounds(499, 279, 287, 25);
		PainelInicio.add(RegistroNome);
		
		RegistroLoja = new JTextField();
		RegistroLoja.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistroLoja.setColumns(10);
		RegistroLoja.setBounds(499, 313, 287, 25);
		PainelInicio.add(RegistroLoja);
		
		RegistroEMail = new JTextField();
		RegistroEMail.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistroEMail.setColumns(10);
		RegistroEMail.setBounds(499, 346, 287, 25);
		PainelInicio.add(RegistroEMail);
		
		RegistroSenha = new JPasswordField();
		RegistroSenha.setFont(new Font("Arial", Font.PLAIN, 12));
		RegistroSenha.setBounds(499, 381, 287, 25);
		PainelInicio.add(RegistroSenha);
		
		JButton BtnLogin = new JButton("");
		BtnLogin.setBorderPainted(false);
		BtnLogin.setBorder(null);
		BtnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Logar();
			}
		});
		BtnLogin.setIcon(new ImageIcon(Inicio.class.getResource("/icones/Login - Ayffir.png")));
		BtnLogin.setBounds(302, 384, 90, 25);
		PainelInicio.add(BtnLogin);
		
		JButton BtnRegistrar = new JButton("");
		BtnRegistrar.setBorder(null);
		BtnRegistrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		BtnRegistrar.setBorderPainted(false);
		BtnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrar();
			}
		});
		BtnRegistrar.setIcon(new ImageIcon(Inicio.class.getResource("/icones/Registrar - Ayffir.png")));
		BtnRegistrar.setBounds(696, 417, 90, 25);
		PainelInicio.add(BtnRegistrar);
		
		JLabel TextoVersao = new JLabel("Vers\u00E3o:");
		TextoVersao.setFont(new Font("Arial", Font.BOLD, 12));
		TextoVersao.setForeground(Color.DARK_GRAY);
		TextoVersao.setBounds(3, 556, 44, 15);
		PainelInicio.add(TextoVersao);
		
		TextoVersaoAtual = new JLabel("0.0.0.1");
		TextoVersaoAtual.setForeground(Color.DARK_GRAY);
		TextoVersaoAtual.setFont(new Font("Arial", Font.PLAIN, 12));
		TextoVersaoAtual.setBounds(49, 556, 737, 15);
		PainelInicio.add(TextoVersaoAtual);
		
		JLabel Fundo = new JLabel("");
		Fundo.setIcon(new ImageIcon(Inicio.class.getResource("/icones/FundoInicio - Ayffir.png")));
		Fundo.setBounds(0, 0, 800, 571);
		PainelInicio.add(Fundo);
		
		conexao = ModuloConexao.conector();
	}
}
