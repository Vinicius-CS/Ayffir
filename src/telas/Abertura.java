package telas;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import telas.Abertura;
import telas.Atualizacao;
import telas.AtualizacaoObrigatoria;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import conexao.ModuloConexao;
import java.awt.Font;
import java.awt.Color;

public class Abertura extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel PainelInicio;
	private JLabel TextoVersaoAtual;
	
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
					Abertura frame = new Abertura();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	String AtualizacaoObrigatoria;
	private String getAtualizacaoObrigatoria() {
		return AtualizacaoObrigatoria;
	}

	private void setAtualizacaoObrigatoria(String atualizacaoobrigatoria) {
		AtualizacaoObrigatoria = atualizacaoobrigatoria;
	}

	String Versao;
	private String getVersao() {
		return Versao;
	}

	private void setVersao(String versao) {
		Versao = versao;
	}

	public void Atualizacao() {
		String sql = "select Versao from atualizacao";
		
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				this.setVersao(rs.getString("Versao"));
				Atualizacao3();
			}
			
		} catch (Exception e) {
		}
	}
	
	public void Atualizacao2() throws InterruptedException {
		if(this.getVersao().equals("0.0.0.1")) {
			abertura();
		}else{
			if(this.getAtualizacaoObrigatoria().equals("1")) {
				AtualizacaoObrigatoria atualizacaoobrigatoria = new AtualizacaoObrigatoria();
				atualizacaoobrigatoria.setVisible(true);
				dispose();
			}else {
				if(this.getAtualizacaoObrigatoria().equals("0")) {
					Atualizacao atualizacao = new Atualizacao();
					atualizacao.setVisible(true);
					dispose();
				}
			}
			
			}
		}
	
	public void Atualizacao3() {
		String sql = "select AtualizacaoObrigatoria from atualizacao";
		
		try {
			pst = conexao.prepareStatement(sql);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				this.setAtualizacaoObrigatoria(rs.getString("AtualizacaoObrigatoria"));
				Atualizacao2();
			}
			
		} catch (Exception e) {
		}
	}

	private void abertura() throws InterruptedException {
        Thread.sleep(3000); //3 segundos
        Inicio inicio = new Inicio( );
        inicio.setVisible( true );
        this.dispose();
    }
	/**
	 * Create the frame.
	 */
	public Abertura() {
		setUndecorated(true);
		setBackground(new Color(0f,0f,0f,0f));
		setUndecorated(true);
		setName("");
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
					Atualizacao();
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Abertura.class.getResource("/icones/LogoMenorAinda - Ayffir.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Ayffir");
		setBounds(550, 350, 292, 92);
		PainelInicio = new JPanel();
		PainelInicio.setBackground(new Color(0f,0f,0f,0f));
		PainelInicio.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(PainelInicio);
		PainelInicio.setLayout(null);
		
		JLabel TextoVersao = new JLabel("Vers\u00E3o:");
		TextoVersao.setFont(new Font("Arial", Font.BOLD, 12));
		TextoVersao.setForeground(Color.DARK_GRAY);
		TextoVersao.setBounds(3, 585, 44, 15);
		PainelInicio.add(TextoVersao);
		
		TextoVersaoAtual = new JLabel("0.0.0.1");
		TextoVersaoAtual.setForeground(Color.DARK_GRAY);
		TextoVersaoAtual.setFont(new Font("Arial", Font.PLAIN, 12));
		TextoVersaoAtual.setBounds(49, 585, 737, 15);
		PainelInicio.add(TextoVersaoAtual);
		
		JLabel Fundo = new JLabel("");
		Fundo.setIcon(new ImageIcon(Abertura.class.getResource("/icones/FundoAbertura - Ayffir.png")));
		Fundo.setBounds(0, 0, 292, 92);
		PainelInicio.add(Fundo);
		
		conexao = ModuloConexao.conector();
	}
}
