package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;


/**
 * Frame unico per tutta l'interfaccia grafica,
 * contiene il pannello per la divisone dei file
 * e il pannello per la ricomposizione di un file,
 * questi 2 pannelli vengono intercambiati usando una JTabbedPanel
 * @author Filippo Altimani
 *
 */
public class JFrmMain extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Pannello contenente l'interfaccia grafica per la
	 * divisione dei file
	 */
	private JPnlSplit pnlD;
	
	/**
	 * Pannello contenente l'interfaccia grafica per la
	 * ricomposizione di un file
	 */
	private JPnlMerge pnlR;
	
	public JFrmMain() {
		
		super("File splitter");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		JTabbedPane tbp = new JTabbedPane();
		pnlD = new JPnlSplit();
		pnlR = new JPnlMerge();
		
		tbp.addTab("Dividi", pnlD);
		tbp.addTab("Ricomponi", pnlR);
		
		this.add(tbp, BorderLayout.CENTER);
		
		this.pack();
	}

	

}
