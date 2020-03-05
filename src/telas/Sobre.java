package telas;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Sobre extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre frame = new Sobre();
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
	public Sobre() {
		setBounds(-5, -28, 805, 600);
		getContentPane().setLayout(null);
		
		JLabel Fundo = new JLabel("");
		Fundo.setIcon(new ImageIcon(Sobre.class.getResource("/icones/FundoSobre - Ayffir.png")));
		Fundo.setBounds(0, 0, 800, 571);
		getContentPane().add(Fundo);

	}
}
