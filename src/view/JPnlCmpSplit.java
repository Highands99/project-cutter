package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import split.CompressionSplitter;
import split.Splitter;


/**
 * Pannello per la modifica/creazione del CompressionSplitter
 * @author Filippo Altimani
 * @see CompressionSplitter
 */
public class JPnlCmpSplit extends JPnlOpSplit implements ActionListener  {
	
	private static final long serialVersionUID = 7L;
	
	/**
	 * Text box per l'inserimento della dimensione di una parte nella lista
	 */
	private JTextField txfSize;
	
	/**
	 * Text box (disabilitata) per mostrare la differenza tra la dimensione del
	 * file da dividere e la somma delle dimensioni per ogni parte 
	 * contenute nella lista
	 */
	private JTextField txfRemainSize;
	
	/**
	 * Pulsante per aggiungere la dimensione contenuta {@link #txfSize}
	 * nella lista
	 */
	private JButton btnAddPart;
	
	/**
	 * Pulsante per rimuovere dalla lista la dimensione selezionata nella {@link #cmbParts}
	 */
	private JButton btnRmvPart;
	
	/**
	 * Lista delle dimensioni per ogni parte
	 */
	private JComboBox<Long> cmbParts;
	
	/**
	 * CompressionSplitter da modificare/creare
	 */
	private transient CompressionSplitter spl;
	
	
	public JPnlCmpSplit () {
		super();
	}
	
	
	public JPnlCmpSplit (String s) {	
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
		txfSize = new JTextField("",4);
		btnAddPart = new JButton("+");
		cmbParts = new JComboBox<>();
		btnRmvPart = new JButton("-");
		JPanel pnlRemainSize = new JPanel();
		JLabel lblRemainSize = new JLabel("Dimensione rimasta (B):");
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
	
	
	/**
	 * Lo splitter in ingresso deve essere del tipo CompressionSplitter
	 */
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
	
	
	/**
	 * Svuota la lista e la text box per l'inserimento 
	 */
	protected void setAllEnabled(boolean flag) {
		txfSize.setEnabled(flag);
		btnAddPart.setEnabled(flag);
		cmbParts.setEnabled(flag);
		btnRmvPart.setEnabled(flag);
		txfRemainSize.setEnabled(flag);
		
		if (flag && this.spl != null) {
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
		if (e.getActionCommand().equals("+") && isNumeric(txfSize.getText())){
			Long size = Long.parseLong(txfSize.getText());
			if (size > 0 && (Long.parseLong(txfRemainSize.getText())-size) >= 0) {
				cmbParts.addItem(size);
				cmbParts.setSelectedIndex(cmbParts.getItemCount()-1);
				this.setRemainSize();
			}
			
		} else if (e.getActionCommand().equals("-") && cmbParts.getSelectedIndex()>=0){
			cmbParts.removeItemAt(cmbParts.getSelectedIndex());
			cmbParts.setSelectedIndex(cmbParts.getItemCount()-1);
			this.setRemainSize();
		}
	}
	
	
	/**
	 * Calcola la differenza tra la dimensione del file da dividere e la somma delle
	 * dimensioni nella {@link #cmbParts} e disabilita o abilita i pulsanti per
	 * l'inserimento delle dimensioni
	 */
	private void setRemainSize() {
		int size = cmbParts.getItemCount();
		int totSize = 0;
		for (int i = 0; i < size; i++) 
			totSize += cmbParts.getItemAt(i);
			
		int remainSize = (int)this.spl.getFileSize()-totSize;
		
		btnAddPart.setEnabled(remainSize>0);
		
		btnRmvPart.setEnabled(totSize!=0);
		
		txfRemainSize.setText(Integer.toString(remainSize));
	}
	
	
	public CompressionSplitter getSplitter() {
		if (this.spl != null) {
			int size = cmbParts.getItemCount();
			ArrayList<Long> ps = new ArrayList<>();
			for (int i = 0; i < size; i++)
				ps.add(cmbParts.getItemAt(i));
			this.spl.setPartsList(ps);
		}
		return this.spl;
	}
}
