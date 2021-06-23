package merge;

import java.io.File;

/**
 * Classe astratta per permettere di ottenere ottenere il
 * file originale unendo le parti divise in precedenza
 * @author Filippo Altimani
 *
 */
public abstract class Merger {
	
	/**
	 * File originale da creare
	 */
	protected File originalFile;
	
	/**
	 * Crea l'oggetto con il nome del file da ricomporre
	 * @param originalFilePath Nome del file da ricomporre
	 * @see #setOriginalFile(String)
	 */
	protected Merger (String originalFilePath) {
		this.setOriginalFile(originalFilePath);
	}
	
	/**
	 * Inizializza il file originale
	 * @param originalFilePath (percorso + nome) del file originale
	 */
	public void setOriginalFile(String originalFilePath) {
		this.originalFile = new File(originalFilePath);
	}
	
	/**
	 * Controlla che il file originale non esista nella cartella contenete le parti
	 * @return Una stringa vuota "" se e' tutto pronto, oppure una stringa contenente l'errore riscontrato
	 */
	public String check() {
		String returnMessage = "";
		
		if (this.originalFile.exists())
			returnMessage = "File originale già presente, impossibile effettuare la ricomposizione\n";
		
		return returnMessage;
	}
	
	/**
	 * Crea il file originale e ci scrive il contenuto delle parti trovate
	 * @return Una stringa vuota "" se tutto e' andato a buon fine, oppure una stringa contenente l'errore riscontrato
	 */
	public abstract String merge();
}
