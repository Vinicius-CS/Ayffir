package telas;

import java.awt.EventQueue;
import javax.swing.JInternalFrame;

public class Estatisticas extends JInternalFrame {

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
					Estatisticas frame = new Estatisticas();
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
	public Estatisticas() {
		setBounds(-5, -28, 805, 600);
		getContentPane().setLayout(null);

	}
}
