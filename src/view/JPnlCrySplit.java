package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import split.CryptSplitter;
import split.Splitter;


/**
 * Pannello per la modifica/creazione del CryptSplitter
 * @author Filippo Altimani
 * @see CryptSplitter
 */
public class JPnlCrySplit  extends JPnlOpSplit {
	
	private static final long serialVersionUID = 6L;
	
	/**
	 * Text box per l'inserimento della dimensione di ogni parte
	 */
	private JTextField txfSize;
	
	/**
	 * Text box per l'inserimento della password con coi codificare le parti
	 */
	private JTextField txfKey;
	
	/**
	 * CryptSplitter da modificare/creare
	 */
	private transient CryptSplitter spl;
	
	
	public JPnlCrySplit() {
		super();
	}
	
	
	public JPnlCrySplit(String s) {			
		super(s);
	}
	
	
	/**
	 * Il layout del pannello e il BoxLayout
	 * @see BoxLayout
	 */
	protected void initialize() {		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel pnlSize = new JPanel();
		JLabel lblSize = new JLabel("Dimensione (B):");
		txfSize = new JTextField("", 10);
		JPanel pnlKey = new JPanel();
		JLabel lblKey = new JLabel("Chiave (16 chr):");
		txfKey = new JTextField("", 14);
		
		txfSize.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((e.getKeyChar()<'0' || e.getKeyChar()>'9') &&
					 e.getKeyChar()!=KeyEvent.VK_BACK_SPACE && e.getKeyChar()!=KeyEvent.VK_DELETE)
					e.consume();	
			}
		});
		
		txfKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txfKey.getText().length()>15)
					e.consume();	
			}
		});
		
		pnlSize.add(lblSize);
		pnlSize.add(txfSize);
		
		pnlKey.add(lblKey);
		pnlKey.add(txfKey);
		
		this.add(pnlSize);
		this.add(pnlKey);
	}
	
	
	public void setSplitter(Splitter s) {
		if (s instanceof CryptSplitter) {
			this.spl = (CryptSplitter) s;
			this.setAllEnabled(true);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	public void setSplitter(String s) {
		if (s.length()>0) {
			CryptSplitter sp = new CryptSplitter(s);
			this.setSplitter(sp);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	protected void setAllEnabled(boolean flag) {
		txfSize.setEnabled(flag);
		txfKey.setEnabled(flag);
		if (flag) {
			txfSize.setText(Long.toString(spl.getPartSize()));
			txfKey.setText(spl.getKey());
		}
		else {
			txfSize.setText("");
			txfKey.setText("");
		}
	}
	
	
	public CryptSplitter getSplitter() {
		if (this.spl != null) {
			if (isNumeric(txfSize.getText())) {
				this.spl.setPartSize(Integer.parseInt(txfSize.getText()));
				this.spl.setKey(txfKey.getText());
			} else 
				this.spl.setPartSize(0);
		}

		return this.spl;
	}
}
