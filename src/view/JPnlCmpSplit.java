package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.CompressionSplitter;
import model.Splitter;

public class JPnlCmpSplit extends JPnlOpSplit implements ActionListener  {
	
	private static final long serialVersionUID = 7L;
	private JLabel lblSize, lblRemainSize;
	private JTextField txfSize, txfRemainSize;
	private JButton btnAddPart, btnRmvPart;
	private JComboBox<Long> cmbParts;
	private CompressionSplitter spl;
	
	
	public JPnlCmpSplit () {
		super();
	}
	
	
	public JPnlCmpSplit (String s) {	
		super(s);
	}
	
	
	protected void initialize() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel pnlSize = new JPanel();
		lblSize = new JLabel("Dimensione (B):");
		txfSize = new JTextField("",4);
		btnAddPart = new JButton("+");
		cmbParts = new JComboBox<Long>();
		btnRmvPart = new JButton("-");
		JPanel pnlRemainSize = new JPanel();
		lblRemainSize = new JLabel("Dimensione rimasta (B):");
		txfRemainSize = new JTextField("", 7);
		
		btnAddPart.addActionListener(this);
		btnRmvPart.addActionListener(this);
		
		txfRemainSize.setEditable(false);
		
		
		pnlSize.add(lblSize);
		pnlSize.add(txfSize);
		pnlSize.add(btnAddPart);
		pnlSize.add(btnRmvPart);
		pnlSize.add(cmbParts);
		
		pnlRemainSize.add(lblRemainSize);
		pnlRemainSize.add(txfRemainSize);
		
		this.add(pnlSize);
		this.add(pnlRemainSize);
	}
	
	
	public void setSplitter(Splitter s) {
		if (s instanceof CompressionSplitter) {
			this.spl = (CompressionSplitter) s;
			this.setAllEnabled(true);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	public void setSplitter(String s) {
		if (s.length()>0) {
			CompressionSplitter sp = new CompressionSplitter(s);
			this.setSplitter(sp);
		}
		else {
			this.spl = null;
			this.setAllEnabled(false);
		}
	}
	
	
	protected void setAllEnabled(boolean flag) {
		txfSize.setEnabled(flag);
		btnAddPart.setEnabled(flag);
		cmbParts.setEnabled(flag);
		btnRmvPart.setEnabled(flag);
		txfRemainSize.setEnabled(flag);
		
		if (flag) {
			cmbParts.removeAllItems();
			Long size = this.spl.getPartSize(0);
			int i = 0;
			while (size != 0) {
				cmbParts.addItem(size);
				i++;
				size = this.spl.getPartSize(i);
			}
			this.setRemainSize();
		}
		else {
			cmbParts.removeAllItems();
			txfSize.setText("");
			txfRemainSize.setText("");
		}
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("+")){
			if (isNumeric(txfSize.getText())){
				Long size = Long.parseLong(txfSize.getText());
				if (size > 0 && (Long.parseLong(txfRemainSize.getText())-size) >= 0) {
					cmbParts.addItem(size);
					cmbParts.setSelectedIndex(cmbParts.getItemCount()-1);
					this.setRemainSize();
				}
			}
			
		} else if (e.getActionCommand().equals("-")){
			if (cmbParts.getSelectedIndex()>=0) {
				cmbParts.removeItemAt(cmbParts.getSelectedIndex());
				cmbParts.setSelectedIndex(cmbParts.getItemCount()-1);
				this.setRemainSize();
			}
			
		}
	}
	
	
	private void setRemainSize() {
		int size = cmbParts.getItemCount();
		int totSize = 0;
		for (int i = 0; i < size; i++) 
			totSize += cmbParts.getItemAt(i);
			
		int remainSize = (int)this.spl.getFileSize()-totSize;
		
		if (remainSize<=0)
			btnAddPart.setEnabled(false);
		else 
			btnAddPart.setEnabled(true);
		
		if (totSize==0)
			btnRmvPart.setEnabled(false);
		else 
			btnRmvPart.setEnabled(true);
		
		txfRemainSize.setText(Integer.toString(remainSize));
	}
	
	
	public CompressionSplitter getSplitter() {
		if (this.spl != null) {
			int size = cmbParts.getItemCount();
			Vector<Long> ps = new Vector<Long>();
			for (int i = 0; i < size; i++)
				ps.add(cmbParts.getItemAt(i));
			this.spl.setPartsList(ps);
		}
		return this.spl;
	}
}
