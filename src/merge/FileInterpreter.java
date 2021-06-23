package merge;

import split.CryptSplitter;
import split.Splitter;


/**
 * Oggetto per interpretare il tipo di divisione effettuata ad un file 
 * attraverso il nome di una delle sue parti
 * @author Filippo Altimani
 *
 */
public class FileInterpreter {

	/**
	 * Oggetto che permette di ricomporre il file originale
	 */
	private Merger merger;
	
	/**
	 * Percorso + nome del file originale da ricomporre
	 */
	private String originalFilePath;
	
	/**
	 * Costruisce l'oggetto a partire dal filePath
	 * @param filePath Percorso + nome del file contenente una parte del del file originale da ricomporre
	 */
	public FileInterpreter(String filePath) {
		this.setMerger(filePath);
	}
	
	
	/**
	 * Costruisce l'oggetto senza inserire dati
	 */
	public FileInterpreter() {
		this.merger = null;
		this.originalFilePath = "";
	}
	
	
	/**
	 * Inizilizza il merger in base al nome del file passato come parametro
	 * @param filePath Percorso + nome del file contenente una parte del del file originale da ricomporre
	 */
	public void setMerger(String filePath) {
	
		String[] fileNameParts = filePath.split("\\.");

		if (fileNameParts[fileNameParts.length-1].equals("zip") && fileNameParts[fileNameParts.length-2].equals(Splitter.EXTENSION)) {
			this.setOriginalFilePath(fileNameParts, fileNameParts.length-2);
			this.merger = new CompressionMerger(originalFilePath);
		}
		
		else if (fileNameParts[fileNameParts.length-1].equals(Splitter.EXTENSION)) {
			if (fileNameParts[fileNameParts.length-2].equals(CryptSplitter.EXTENSION)) {
				this.setOriginalFilePath(fileNameParts, fileNameParts.length-3);
				this.merger = new CryptMerger(originalFilePath);
			}
			
			else {
				this.setOriginalFilePath(fileNameParts, fileNameParts.length-2);
				this.merger = new DefaultMerger(this.originalFilePath);
			}
		}
		
		else {
			this.merger = null;
			this.originalFilePath = "";
		}

	}
	
	
	/**
	 * Se il tipo del merger lo permette, gli viene passato come parametro
	 * la passaword con la quale decifrare la parti del file originale
	 * @param psw Password per deficrare il contenuto delle parti
	 */
	public void setPassword(String psw) {
		if (merger instanceof CryptMerger)
			((CryptMerger) merger).setKey(psw);
	}
	
	
	/**
	 * Identifica il nome del file originale da ricomporre
	 * @param path Array di stringhe contenete il percorso + nome di una parte del file originale suddiviso (split) attorno alle corrispondenze del punto "."
	 * @param maxIndex Indice che indica la fine del nome del file originale
	 */
	private void setOriginalFilePath(String[] path, int maxIndex) {
		this.originalFilePath = this.originalFilePath.concat(path[0]);
		for (int i=1; i<maxIndex;i++)
			this.originalFilePath = this.originalFilePath.concat("." + path[i]);
	}
	
	
	/**
	 * Controlla che il merger sia stato inizializzato correttamente
	 * @return Stringa contenente il messaggio di errore, oppure stringa vuota "" se il merger è stato inizializzato correttamente
	 */
	public String check() {
		if (this.merger != null)
			return this.merger.check();
		else
			return "Specificare il file contenente una parte del file da ricomporre";
	}
	
	
	
	/**
	 * Richiama il metodo del merger per ricomporre il file originale 
	 * @return Stringa contenente il messaggio di errore, oppure stringa vuota "" se il file originale è stato ricomposto correttamente
	 */
	public String merge() {
		return this.merger.merge();
	}
}
