package view;

import javax.swing.*;

import merge.FileInterpreter;

import java.awt.Dimension;
import java.awt.event.*;


/**
 * Pannello per ricomporre un file scegliendo una parte del file da ricomporre ed
 * eventualmente la password per decriptare il contenuto delle parti
 * @author Filippo Altimani
 * @see merge.Merger
 * @see FileInterpreter
 */
public class JPnlMerge extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 3L;
	
	/**
	 * Text box (disabilitata) per mostrare il percorso della perte del file
	 * da ricomporre
	 */
	private JTextField txfSrc;
	
	/**
	 * Text box per l'inserimento della password con la quale eventualmente decifrare
	 * il contenuto delle parti del file da ricomporre
	 */
	private JTextField txfKey;
	
	/**
	 * Pulsante per aprire l'esplora risorse e selezionare
	 * la parte del file che si desidere ricomporre
	 */
	private JButton btnSrc;
	
	/**
	 * Pulsante per avviare ricomposizione
	 */
	private JButton btnRun;
	
	/**
	 * Oggetto in grado di capire il tipo di divisione ricevuta da un file
	 * partendo dal nome di una delle sue parti
	 */
	private transient FileInterpreter interpreter;
	
	/**
	 * Il layout del pannello e il BoxLayout
	 * @see BoxLayout
	 */
	public JPnlMerge() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel pnlSrc = new JPanel();
		txfSrc = new JTextField("",25);
		btnSrc = new JButton("Cerca");
		JPanel pnlKey = new JPanel();
		JLabel lblKey = new JLabel("Chiave (16 chr):");
		txfKey = new JTextField("", 14);
		btnRun = new JButton("Ricomponi");
		interpreter = new FileInterpreter();
		
		btnSrc.addActionListener(this);
		btnRun.addActionListener(this);
		
		txfKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txfKey.getText().length()>15)
					e.consume();	
			}
		});
		
		txfSrc.setEnabled(false);
		btnRun.setEnabled(false);
		
		pnlSrc.add(txfSrc);
		pnlSrc.add(btnSrc);
		
		pnlKey.add(lblKey);
		pnlKey.add(txfKey);

		btnRun.setAlignmentX(CENTER_ALIGNMENT);

		this.add(Box.createRigidArea(new Dimension(0,20)));
		this.add(pnlSrc);
		this.add(pnlKey);
		this.add(btnRun);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Cerca")) {
			JFileChooser fc = new JFileChooser();
			int response = fc.showOpenDialog(this);
			
			if (response == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().length()>0) {
					
				this.interpreter.setMerger(fc.getSelectedFile().getAbsolutePath());
				
				this.txfSrc.setText(fc.getSelectedFile().getAbsolutePath());
				this.btnRun.setEnabled(true);
	        }
		}
		
		else if (e.getActionCommand().equals("Ricomponi")) {
			
			if (!this.txfKey.getText().equals(""))
				this.interpreter.setPassword(txfKey.getText());
			
			String warningMessage = this.interpreter.check();
			
			if (!warningMessage.equals(""))
				JOptionPane.showMessageDialog(null, warningMessage, "Errore inserimento dati", JOptionPane.ERROR_MESSAGE);
			else {
				String errorMessage = this.interpreter.merge();
				if (!errorMessage.equals(""))
					JOptionPane.showMessageDialog(null, errorMessage, "Errore ricomposizione file", JOptionPane.ERROR_MESSAGE);
				else
					JOptionPane.showMessageDialog(null, "Ricomposizione file terminata", "Ricomposizione terminata", JOptionPane.INFORMATION_MESSAGE);
				
				this.btnRun.setEnabled(false);
				this.txfKey.setText("");
				this.txfSrc.setText("");
				this.interpreter = new FileInterpreter();
			}
		}
	}
}
