package view;

import javax.swing.JPanel;

import split.Splitter;


/**
 * Pannello (astratto) che consete di inserire le informazione con le quali
 * dividere un file
 * @author Filippo Altimani
 *
 */
public abstract class JPnlOpSplit extends JPanel {
	
	private static final long serialVersionUID = 4L;
	
	/**
	 * Crea il pannello senza informazioni e con i componenti disabilitati
	 */
	protected JPnlOpSplit() {
		this.initialize();
		this.setAllEnabled(false);
	}
	
	/**
	 * Crea il pannello e lo splitter partendo dal percorso file
	 * @param s Percorso del file da dividere
	 * @see #setSplitter(String)
	 */
	protected JPnlOpSplit(String s) {	
		this.initialize();
		this.setSplitter(s);
	}
	
	/**
	 * Inizializza i componenti del pannello
	 */
	protected abstract void initialize();
	
	/**
	 * Restituisce lo splitter con le informazioni inserite nei campi del pannello
 	 * @return Splitter conle informazioni inserite dall'utente
	 */
	public abstract Splitter getSplitter();
	
	/**
	 * Permette di riempire i campi del pannello con le informazioni contenute
	 * nello splitter in ingresso. Se lo splitter non è del tipo corretto,
	 * viene chiamato il metodo {@link #setAllEnabled(boolean)} false
	 * e imposta a null lo splitter
	 * @param s splitter 
	 */
	public abstract void setSplitter(Splitter s);
	
	/**
	 * Permette di creare un nuovo splitter partendo del percorso
	 * del file da dividere. Se la stringa è vuota
	 * viene chiamato il metodo {@link #setAllEnabled(boolean)} false
	 * e imposta a null lo splitter
	 * @param s percorso del file da dividere
	 */
	public abstract void setSplitter(String s);
	
	/**
	 * Abilita e disabilita tutti i componendi, nel caso li disabiliti
	 * li svuota da ogni informazione
	 * @param flag true per abilitare, false per disabilitare
	 */
	protected abstract void setAllEnabled(boolean flag);
	
	/**
	 * Verifica che nelle text box siano stati inseriti solo dei numeri
	 * @param str Il testo all'interno della text box
	 * @return true se nella text box ci sono solamente numeri altrimenti false
	 */
	public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        
        return true;
    }
}
