package view;

import javax.swing.*;

import merge.FileInterpreter;

import java.awt.Dimension;
import java.awt.event.*;


public class JPnlMerge extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 3L;
	
	private JTextField txfSrc, txfKey;
	private JLabel lblKey;
	private JButton btnSrc, btnRun;
	private FileInterpreter interpreter;
	
	public JPnlMerge() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel pnlSrc = new JPanel();
		txfSrc = new JTextField("",25);
		btnSrc = new JButton("Cerca");
		JPanel pnlKey = new JPanel();
		lblKey = new JLabel("Chiave (16 chr):");
		txfKey = new JTextField("", 14);
		btnRun = new JButton("Ricomponi");
		
		btnSrc.addActionListener(this);
		btnRun.addActionListener(this);
		
		txfKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txfKey.getText().length()>15)
					e.consume();	
			}
		});
		
		pnlSrc.add(txfSrc);
		pnlSrc.add(btnSrc);
		
		pnlKey.add(lblKey);
		pnlKey.add(txfKey);

		btnRun.setAlignmentX(CENTER_ALIGNMENT);

		//this.add(new JLabel("Inserire il percorso del file che contiene la prima parte del file da ricostruire"));
		this.add(Box.createRigidArea(new Dimension(0,20)));
		this.add(pnlSrc);
		this.add(pnlKey);
		this.add(btnRun);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cerca")) {
			JFileChooser fc = new JFileChooser();
			int response = fc.showOpenDialog(this);
			
			if (response == JFileChooser.APPROVE_OPTION) {
				if (fc.getSelectedFile().length()>0) {
					FileInterpreter fi = new FileInterpreter(fc.getSelectedFile().getAbsolutePath());
					if (!this.txfKey.getText().equals(""))
						fi.setPassword(txfKey.getText());
					
					this.interpreter = fi;
				}
	        }
		}
		
		else if (e.getActionCommand().equals("Ricomponi")) {
			String warningMessage = this.interpreter.check();
			
			if (!warningMessage.equals(""))
				JOptionPane.showMessageDialog(null, warningMessage, "Errore inserimento dati", JOptionPane.ERROR_MESSAGE);
			else {
				String errorMessage = this.interpreter.merge();
				if (!errorMessage.equals(""))
					JOptionPane.showMessageDialog(null, errorMessage, "Errore ricomposizione file", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
