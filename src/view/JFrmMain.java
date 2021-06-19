package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class JFrmMain extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JPnlSplit pnlD;
	private JPnlMerge pnlR;
	
	public JFrmMain() {
		
		super("File splitter");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tbp = new JTabbedPane();
		pnlD = new JPnlSplit();
		pnlR = new JPnlMerge();
		
		tbp.addTab("Dividi", pnlD);
		tbp.addTab("Ricomponi", pnlR);
		
		this.add(tbp, BorderLayout.CENTER);
		
		this.pack();
	}

	

}
