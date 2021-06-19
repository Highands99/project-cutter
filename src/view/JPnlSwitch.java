package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import split.*;

public class JPnlSwitch extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 9L;
	private JRadioButton rdbDefOp, rdbCryOp, rdbCmpOp, rdbNpOp;
	private JPanel pnlCards;
	private JPnlOpSplit pnlDef, pnlCry, pnlCmp, pnlNP;
	private String lastRdbSelected, lastFileSelected;
	
	
	public JPnlSwitch() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		JPanel pnlRDB = new JPanel();
		rdbDefOp = new JRadioButton("Default");
		rdbCryOp = new JRadioButton("Cripta");
		rdbCmpOp = new JRadioButton("Comprimi");
		rdbNpOp = new JRadioButton("N. parti");
		
		pnlCards = new JPanel(new CardLayout());
		pnlDef = new JPnlDefSplit();
		pnlCry = new JPnlCrySplit();
		pnlCmp = new JPnlCmpSplit();
		pnlNP = new JPnlNPSplit();
		
		rdbDefOp.addActionListener(this);
		rdbCryOp.addActionListener(this);
		rdbCmpOp.addActionListener(this);
		rdbNpOp.addActionListener(this);
		
		ButtonGroup grpOp = new ButtonGroup();
		grpOp.add(rdbDefOp);
		grpOp.add(rdbCryOp);
		grpOp.add(rdbCmpOp);
		grpOp.add(rdbNpOp);
		rdbDefOp.setSelected(true);//seleziono l'operazione di default
		this.lastRdbSelected = rdbDefOp.getText();
		//peronalizzazione tooltip per ogni radio button
		String s = "Divisione in più parti specificando la dimensione ";
		rdbDefOp.setToolTipText(s+"uguale per ogni parte");
		rdbCryOp.setToolTipText(s+"uguale per ogni parte e crittografando i file tramite una chiave");
		rdbCmpOp.setToolTipText(s+"di ogni parte e comprimendo il contenuto dei file generati");
		rdbNpOp.setToolTipText("Divisione in più parti specificando il numero di parti");
		rdbDefOp.setEnabled(false);
		rdbCryOp.setEnabled(false);
		rdbCmpOp.setEnabled(false);
		rdbNpOp.setEnabled(false);
		
		//allineo i radio button
		pnlRDB.add(rdbDefOp);
		pnlRDB.add(rdbCryOp);
		pnlRDB.add(rdbCmpOp);
		pnlRDB.add(rdbNpOp);
		
		pnlCards.add(pnlDef,rdbDefOp.getText());
		pnlCards.add(pnlCry,rdbCryOp.getText());
		pnlCards.add(pnlCmp,rdbCmpOp.getText());
		pnlCards.add(pnlNP,rdbNpOp.getText());
		
		this.add(pnlRDB);
		this.add(pnlCards);
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (!e.getActionCommand().equals(this.lastRdbSelected)) {
			this.lastRdbSelected = e.getActionCommand();
			CardLayout cl = (CardLayout) (pnlCards.getLayout());
			
			if (e.getActionCommand().equals(rdbDefOp.getText())) {
				pnlDef.setSplitter(lastFileSelected);
			}
			
			else if (e.getActionCommand().equals(rdbCryOp.getText())) {
				pnlCry.setSplitter(lastFileSelected);
			}
	
			else if (e.getActionCommand().equals(rdbCmpOp.getText())) {
				pnlCmp.setSplitter(lastFileSelected);
			}
			
			else if (e.getActionCommand().equals(rdbNpOp.getText())) {
				pnlNP.setSplitter(lastFileSelected);
			}
			
			cl.show(pnlCards, e.getActionCommand());
		}
	}
	
	
	public void setOperation(Splitter s) {
		CardLayout cl = (CardLayout) (pnlCards.getLayout());
		lastFileSelected = s.getFilePath();
		
		if (s instanceof DefaultSplitter) {
			if (s instanceof CryptSplitter) {
				pnlCry.setSplitter(s);
				lastRdbSelected = rdbCryOp.getText();
				rdbCryOp.setSelected(true);
			}
			else {
				pnlDef.setSplitter(s);
				lastRdbSelected = rdbDefOp.getText();
				rdbDefOp.setSelected(true);
			}
		}
		
		else if (s instanceof CompressionSplitter) {
			pnlCmp.setSplitter(s);
			lastRdbSelected = rdbCmpOp.getText();
			rdbCmpOp.setSelected(true);
		}
		
		else if (s instanceof NPartSplitter) {
			pnlNP.setSplitter(s);
			lastRdbSelected = rdbNpOp.getText();
			rdbNpOp.setSelected(true);
		}
		
		cl.show(pnlCards, lastRdbSelected);
	}
	
	
	public void resetSplitter() {
		pnlDef.setSplitter("");
		pnlCry.setSplitter("");
		pnlCmp.setSplitter("");
		pnlNP.setSplitter("");
	}
	
	
	public Splitter getSplitter() {
		Splitter spl = null;
		
		if (rdbDefOp.isSelected())
			spl = pnlDef.getSplitter();
		
		else if (rdbCryOp.isSelected())
			spl = pnlCry.getSplitter();
		
		else if (rdbCmpOp.isSelected())
			spl = pnlCmp.getSplitter();
		
		else if (rdbNpOp.isSelected())
			spl = pnlNP.getSplitter();
		
		return spl;
	}
	
	
	protected void setAllEnabled(boolean flag) {
		rdbDefOp.setEnabled(flag);
		rdbCryOp.setEnabled(flag);
		rdbCmpOp.setEnabled(flag);
		rdbNpOp.setEnabled(flag);
	}
}
