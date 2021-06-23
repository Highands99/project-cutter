package view;

import javax.swing.*;

import split.*;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


/**
 * Pannello per dividere file in una coda
 * @author Filippo Altimani
 *
 */
public class JPnlSplit extends JPanel implements ActionListener, ItemListener {
	
	private static final long serialVersionUID = 2L;
	
	/**
	 * Progressbar per mostrare l'avanzamento della divisione
	 * dei file nella coda
	 */
	private JProgressBar progressBar;
	
	/**
	 * Pulsante per aggiungere un file alla coda
	 */
	private JButton btnAddF;
	
	/**
	 * Pulsante per eliminare un file dalla coda
	 */
	private JButton btnDelF;
	
	/**
	 * Pulsante per cambiare un file nella coda
	 */
	private JButton btnChgF;
	
	/**
	 * Pulsante per avviare la divisone dei file nella coda
	 */
	private JButton btnRun;
	
	/**
	 * ComboBox per permettere lo spostamento tra i file nella coda
	 */
	private JComboBox<Splitter> cmbFile;
	
	/**
	 * Lista contenere gli oggetti per dividere i file
	 */
	private Vector<Splitter> files;
	
	/**
	 * Pannello per permettere di cambiare ed inserire le modalità di divisione 
	 * dei file nella coda
	 */
	private JPnlSwitch pnlOp;
	
	/**
	 * Indice per controllare l'ultimo file selezionato dalla ComboBox, così
	 * da permetterne la il salvataggio della modifica
	 */
	private int lastItemSelected;

	
	/**
	 * Il layout del pannello e il BoxLayout
	 * @see BoxLayout
	 */
	public JPnlSplit() {
		//imposto BoxLayout verticale sul panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//inizializzo i componenti
		progressBar = new JProgressBar();
		btnRun = new JButton("Dividi");
		files = new Vector<>();
		cmbFile = new JComboBox<>(files);		
		JPanel pnlBtnF = new JPanel();
		btnAddF = new JButton("Aggiungi");
		btnChgF = new JButton("Modifica");
		btnDelF = new JButton("Rimuovi");
		pnlOp = new JPnlSwitch();
		
		btnAddF.addActionListener(this);
		btnChgF.addActionListener(this);
		btnDelF.addActionListener(this);
		btnRun.addActionListener(this);
		cmbFile.addItemListener(this);
		
		this.lastItemSelected = -1; 
		
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		
		btnChgF.setEnabled(false);
		btnDelF.setEnabled(false);
		btnRun.setEnabled(false);
		
		//allineo i pulsanti per la modifica della coda
		pnlBtnF.add(btnAddF);
		pnlBtnF.add(btnChgF);
		pnlBtnF.add(btnDelF);
		
		//centro i componenti fuori da panel secondari
		cmbFile.setAlignmentX(CENTER_ALIGNMENT);
		btnRun.setAlignmentX(CENTER_ALIGNMENT);
		
		//impilo i componenti
		this.add(progressBar);
		this.add(Box.createRigidArea(new Dimension(0,5)));
		this.add(btnRun);
		this.add(Box.createRigidArea(new Dimension(0,5)));
		this.add(cmbFile);
		this.add(pnlBtnF);
		this.add(pnlOp);
	}
	
	
	/**
	 * Aggiorna lo splitter attuale con le informazioni inserite dall'utente
	 */
	private void saveChanges() {
		if (this.lastItemSelected>=0) {
			Splitter spl = pnlOp.getSplitter();

			if (spl != null && spl.getFilePath().equals(this.files.get(this.lastItemSelected).getFilePath())) 
				this.files.set(this.lastItemSelected, spl);
				
		}
	}
	
	
	/**
	 * Cambia le informazioni mostrate in base allo splitter selezionato
	 */
	public void itemStateChanged(ItemEvent e) {
		if (cmbFile.getSelectedIndex()>=0) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				this.lastItemSelected = cmbFile.getSelectedIndex();
				pnlOp.setOperation(this.files.get(cmbFile.getSelectedIndex()));
			}
			else if (e.getStateChange() == ItemEvent.DESELECTED) 
				this.saveChanges();
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Modifica") && cmbFile.getSelectedIndex()>=0) {
			this.changeFile();
			
		}else if (e.getActionCommand().equals("Aggiungi")) {	
			this.addFiles();
			
		} else if (e.getActionCommand().equals("Rimuovi")) {
			this.deleteFile();
			
		} else if (e.getActionCommand().equals("Dividi")) {
			this.saveChanges();
			
			String warningMessage = this.checkFiles();
			
			if (!warningMessage.equals("")) 
				JOptionPane.showMessageDialog(null, warningMessage, "Errore inserimento dati", JOptionPane.ERROR_MESSAGE);
			
			else
				this.splitFiles();

		}
	}
	
	
	/**
	 * Cambia il file selezionato da dividere nella coda
	 */
	private void changeFile() {
		JFileChooser fc = new JFileChooser();
		int response = fc.showOpenDialog(this);
		
		if (response == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().length()>0) {
			DefaultSplitter spl = new DefaultSplitter(fc.getSelectedFile().toString());
			pnlOp.resetSplitter();
			
			int selectIndex = cmbFile.getSelectedIndex();
			
			this.files.set(selectIndex, spl);
			
			cmbFile.setSelectedIndex(selectIndex-1);
			cmbFile.setSelectedIndex(selectIndex);
        }
	}
	
	
	/**
	 * Aggiunge un file da dividere nella coda
	 */
	private void addFiles() {
		JFileChooser fc = new JFileChooser();
		fc.setMultiSelectionEnabled(true);
		
		int response = fc.showOpenDialog(this);
		
		if (response == JFileChooser.APPROVE_OPTION) {
			for (File f : fc.getSelectedFiles()) {
				if (f.length()>0) {
					DefaultSplitter spl = new DefaultSplitter(f.toString());
					int selectIndex = this.files.size();

					this.files.add(selectIndex,spl);
					cmbFile.setSelectedIndex(selectIndex);
					
					if (selectIndex<1) {
						btnChgF.setEnabled(true);
						btnDelF.setEnabled(true);
						btnRun.setEnabled(true);
						pnlOp.setAllEnabled(true);
					}
				}
			}
        }
	}
	
	
	/**
	 * Toglie dalla coda un file da dividere
	 */
	private void deleteFile() {
		if (cmbFile.getSelectedIndex()>=0) {
			
			this.files.remove(cmbFile.getSelectedIndex());
			pnlOp.resetSplitter();
			cmbFile.setSelectedIndex(cmbFile.getItemCount()-1);				
			
			if (this.files.isEmpty()) {
				btnChgF.setEnabled(false);
				btnDelF.setEnabled(false);
				btnRun.setEnabled(false);
				pnlOp.setAllEnabled(false);
			}
		}
	}
	
	
	/**
	 * Controlla che tutti i file nella coda siano pronti per essere divisi
	 * e che non ci sia più di un file nella coda
	 * @return Messaggio di avvertimento se è stato rilevato un problema altrimenti una stringa vuota
	 */
	private String checkFiles() {
		StringBuilder warningBuilder = new StringBuilder();
		ArrayList<String> paths = new  ArrayList<>();
		
		for (Splitter s : files) {
			
			if (JPnlSplit.isFileDuplicate(s.getFilePath(), paths)) 
				warningBuilder.append(s.getFileName() + ": non può esserci lo stesso file più volte nella coda\n");
				
			else 
				paths.add(s.getFilePath());
		}
		
		for (Splitter s : files) {	
			String tmp = s.check();
			if (!tmp.equals(""))
				warningBuilder.append(s.getFileName() + ": " + tmp + "\n");
		}
		
		return warningBuilder.toString();
	}
	
	
	/**
	 * Divide tutti i file nella coda
	 */
	private void splitFiles() {
		StringBuffer errorMessage = new StringBuffer();
		ArrayList<SplitterThread> threads = new ArrayList<>();
		
		for (Splitter s : files)
			threads.add(new SplitterThread(s,errorMessage,progressBar));
		
		int nThreads = threads.size();
		if (nThreads>0) {
			progressBar.setMaximum(nThreads);
			progressBar.setVisible(true);
			
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			
			for (SplitterThread t : threads)
				t.start();
			
			for (SplitterThread t : threads) {
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
					t.interrupt();
				}
			}
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			
			if (errorMessage.length()==0)
				JOptionPane.showMessageDialog(null, "Divisione file terminata", "Divisione terminata", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, errorMessage.toString(), "Errore divisione file", JOptionPane.ERROR_MESSAGE);
			
			files.clear();
			progressBar.setValue(0);
			progressBar.setVisible(false);
			pnlOp.resetSplitter();
			cmbFile.setSelectedIndex(-1);
			btnChgF.setEnabled(false);
			btnDelF.setEnabled(false);
			btnRun.setEnabled(false);
			pnlOp.setAllEnabled(false);
			lastItemSelected = -2;
		}
	}
	
	
	/**
	 * Controlla che la stringa fileName sia presente o meno in listFileName
	 * @param fileName Stringa da controllare
	 * @param listFileName Lista di stringhe
	 * @return False de fileName non è prtesente nella lista true altrimenti
	 */
	private static boolean isFileDuplicate(String fileName, ArrayList<String> listFileName) {
		for (String name : listFileName) {
			if (name.equals(fileName)) 
				return true;
		}
		
		return false;
	}
	
}
