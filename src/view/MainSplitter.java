package view;


/**
 * Avvia l'intero programma
 * @author Filippo Altimani
 * @see JFrmMain
 */
public class MainSplitter {

	public static void main(String[] args) {
		JFrmMain mainF = new JFrmMain();
		
		mainF.setResizable(false);
		mainF.setLocationRelativeTo(null);
			
		mainF.setVisible(true);
	}

}
