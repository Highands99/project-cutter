package view;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.*;


public class JPnlMerge extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 3L;
	
	private JTextField txfSrc;
	private JButton btnSrc, btnRun;
	
	public JPnlMerge() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel pnlSrc = new JPanel();
		txfSrc = new JTextField("",25);
		btnSrc = new JButton("Cerca");
		btnRun = new JButton("Ricomponi");
		
		pnlSrc.add(txfSrc);
		pnlSrc.add(btnSrc);

		btnRun.setAlignmentX(CENTER_ALIGNMENT);

		//this.add(new JLabel("Inserire il percorso del file che contiene la prima parte del file da ricostruire"));
		this.add(Box.createRigidArea(new Dimension(0,20)));
		this.add(pnlSrc);
		this.add(btnRun);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("text")) {
			
		}

	}
}
