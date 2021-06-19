package view;

import model.*;

import javax.swing.*;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


public class JPnlSplit extends JPanel implements ActionListener, ItemListener {
	
	private static final long serialVersionUID = 2L;
	
	private JProgressBar progressBar;
	private JButton btnAddF, btnDelF, btnChgF, btnRun;
	private JComboBox<Splitter> cmbFile;
	private Vector<Splitter> files;
	private JPnlSwitch pnlOp;
	private int lastItemSelected;

	
	public JPnlSplit() {
		//imposto BoxLayout verticale sul panel
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		//inizializzo i componenti
		progressBar = new JProgressBar();
		btnRun = new JButton("Dividi");
		files = new Vector<Splitter>();
		cmbFile = new JComboBox<Splitter>(files);		
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
	
	
	private void saveChanges() {
		if (this.lastItemSelected>=0) {
			Splitter spl = pnlOp.getSplitter();

			if (spl != null && spl.getFilePath() == this.files.get(this.lastItemSelected).getFilePath()) 
				this.files.set(this.lastItemSelected, spl);
				
		}
	}
	
	
	public void itemStateChanged(ItemEvent e) {
		if (cmbFile.getSelectedIndex()>=0) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				this.lastItemSelected = (int) cmbFile.getSelectedIndex();
				pnlOp.setOperation(this.files.get(cmbFile.getSelectedIndex()));
			}
			else if (e.getStateChange() == ItemEvent.DESELECTED) 
				this.saveChanges();
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Modifica") && cmbFile.getSelectedIndex()>=0) {
			JFileChooser fc = new JFileChooser();
			int response = fc.showOpenDialog(this);
			
			if (response == JFileChooser.APPROVE_OPTION) {
				if (fc.getSelectedFile().length()>0) {
					DefaultSplitter spl = new DefaultSplitter(fc.getSelectedFile().toString());
					pnlOp.resetSplitter();
					
					int selectIndex = cmbFile.getSelectedIndex();
					
					this.files.set(selectIndex, spl);
					
					cmbFile.setSelectedIndex(selectIndex-1);
					cmbFile.setSelectedIndex(selectIndex);
				}
	        }
			
		}else if (e.getActionCommand().equals("Aggiungi")) {	
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
			
		} else if (e.getActionCommand().equals("Rimuovi")) {
			if (cmbFile.getSelectedIndex()>=0) {
				
				this.files.remove(cmbFile.getSelectedIndex());
				pnlOp.resetSplitter();
				cmbFile.setSelectedIndex(cmbFile.getItemCount()-1);				
				
				if (this.files.size() < 1) {
					btnChgF.setEnabled(false);
					btnDelF.setEnabled(false);
					btnRun.setEnabled(false);
					pnlOp.setAllEnabled(false);
				}
			}
			
		} else if (e.getActionCommand().equals("Dividi")) {
			this.saveChanges();
			
			String warningMessage = "", tmp;
			
			for (Splitter s : files) {	
				tmp = s.check();
				if (tmp != "")
					warningMessage += s.getFileName() + ": " + s.check() + "\n";
			}
				
			
			if (warningMessage != "") 
				JOptionPane.showMessageDialog(null, warningMessage, "Errore inserimento dati", JOptionPane.ERROR_MESSAGE);
			
			else {
				this.splitFiles();
			}
		}
	}
	
	
	private void splitFiles() {
		StringBuffer errorMessage = new StringBuffer();
		ArrayList<String> paths = new  ArrayList<String>();
		ArrayList<SplitterThread> threads = new ArrayList<SplitterThread>();
		boolean flag = false;
		
		for (Splitter s : files) {
			String tempP = s.getFilePath();
			
			for (String p : paths) {
				if (p.equals(tempP)) {
					flag = true;
					break;
				}
			}
			
			if (flag) 
				flag = false;
			
			else {
				paths.add(tempP);
				threads.add(new SplitterThread(s,errorMessage,progressBar));
			}
		}
		
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
				}
			}
			
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			if (errorMessage.length()==0)
				JOptionPane.showMessageDialog(null, "Divisione file terminata", "Divisione terminata", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(null, errorMessage, "Errore divisione file", JOptionPane.ERROR_MESSAGE);
			
			files.clear();
			progressBar.setValue(0);
			progressBar.setVisible(false);
			pnlOp.resetSplitter();
			cmbFile.setSelectedIndex(-1);
			btnChgF.setEnabled(false);
			btnDelF.setEnabled(false);
			btnRun.setEnabled(false);
			pnlOp.setAllEnabled(false);
		}
	}
}
