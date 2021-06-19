package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.NPartSplitter;
import model.Splitter;

public class JPnlNPSplit  extends JPnlOpSplit {
	
	private static final long serialVersionUID = 8L;
	private JLabel lblNP;
	private JTextField txfNP;
	private NPartSplitter spl;
	
	public JPnlNPSplit() {
		super();
	}
	
	public JPnlNPSplit(String s) {
		super(s);
	}
	
	
	protected void initialize() {	
		lblNP = new JLabel("Numero di parti: ");
		txfNP = new JTextField("", 6);
		
		txfNP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((e.getKeyChar()<'0' || e.getKeyChar()>'9') &&
					 e.getKeyChar()!=KeyEvent.VK_BACK_SPACE && e.getKeyChar()!=KeyEvent.VK_DELETE)
					e.consume();	
			}
		});
		
		this.add(lblNP);
		this.add(txfNP);
	}
	
	
	public void setSplitter(Splitter s) {
		if (s instanceof NPartSplitter) {
			this.spl = (NPartSplitter) s;
			this.setAllEnabled(true);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	public void setSplitter(String s) {
		if (s.length()>0) {
			NPartSplitter sp = new NPartSplitter(s);
			this.setSplitter(sp);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	protected void setAllEnabled(boolean flag) {
		txfNP.setEnabled(flag);
		if (flag)
			txfNP.setText(Integer.toString(spl.getNPart()));
		else
			txfNP.setText("");
	}
	
	
	public NPartSplitter getSplitter() {
		if (this.spl != null) {
			if (isNumeric(txfNP.getText())) 
				this.spl.setNPart(Integer.parseInt(txfNP.getText()));
			else 
				this.spl.setNPart(2);
		}
		return this.spl;
	}
}
