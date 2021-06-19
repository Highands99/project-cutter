package view;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.*;

public class JPnlDefSplit extends JPnlOpSplit {
	
	private static final long serialVersionUID = 5L;
	private JLabel lblSize;
	private JTextField txfSize;
	private DefaultSplitter spl;
	
	
	public JPnlDefSplit() {
		super();
	}
	
	
	public JPnlDefSplit(String s) {	
		super(s);
	}
	
	
	protected void initialize() {
		lblSize = new JLabel("Dimensione (B): ");
		txfSize = new JTextField("", 10);
		
		txfSize.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((e.getKeyChar()<'0' || e.getKeyChar()>'9') &&
					 e.getKeyChar()!=KeyEvent.VK_BACK_SPACE && e.getKeyChar()!=KeyEvent.VK_DELETE)
					e.consume();	
			}
		});
		
		this.add(lblSize);
		this.add(txfSize);
	}
	
	
	public void setSplitter(Splitter s) {
		if (s instanceof DefaultSplitter) {
			this.spl = (DefaultSplitter) s;
			this.setAllEnabled(true);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	public void setSplitter(String s) {
		if (s.length()>0) {
			DefaultSplitter sp = new DefaultSplitter(s);
			this.setSplitter(sp);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	protected void setAllEnabled(boolean flag) {
		txfSize.setEnabled(flag);
		if (flag)
			txfSize.setText(Long.toString(spl.getPartSize()));
		else
			txfSize.setText("");
	}
	
	
	public DefaultSplitter getSplitter() {
		if (this.spl != null) {
			if (isNumeric(txfSize.getText())) 
				this.spl.setPartSize(Integer.parseInt(txfSize.getText()));
			else 
				this.spl.setPartSize(0);
		}
	
		return this.spl;
	}
	
}
