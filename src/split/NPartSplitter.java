package split;


/**
 * Oggetto che consente la divisione di un file in più parti specificando
 * il numero di parti
 * @author Filippo Altimani
 *
 */
public class NPartSplitter extends Splitter {
	
	/**
	 * Numero di parti di dimensione uguale
	 */
	private int nPart;
	
	
	/**
	 * Costruisce a partire dal percorso del file da dividere e inizializza
	 * il numero delle parti a 2
	 * @param fp Percorso del file da dividere
	 */
	public NPartSplitter(String fp) {
		super(fp);
		this.nPart = 2;
	}
	
	
	/**
	 * Costruisce a partire dal percorso del file da dividere e imposta 
	 * il numero di parti
	 * @param fp Percorso del file da dividere
	 * @param np Numero di parti
	 */
	public NPartSplitter(String fp, int np) {
		super(fp);
		this.setNPart(np);
	}
	

	/**
	 * Ritorna il numero delle parti
	 * @return Numero di parti
	 */
	public int getNPart() {
		return nPart;
	}


	/**
	 * Imposta il numero di parti ch deve essere maggiore di 1
	 * @param np Numero di parti
	 */
	public void setNPart(int np) {
		if (np>1)
			this.nPart = np;
		else
			this.nPart = 2;
	}
	
	
	/**
	 * Controlla che il numero di parti non sia superiore alla dimensione 
	 * del file da dividere e che il numero di parti non sia inferiore
	 * a 2
	 */
	@Override
	public String check() {
		String returnMessage = super.check();
		
		if(this.nPart<2 || this.nPart>this.getFileSize())
			returnMessage += "Numero di parti errato\n";
		
		return returnMessage;
	}
	
	
	/**
	 * Ritorna la dimensione, in byte, delle parti
	 */
	public long getPartSize() {
		long fileSize = this.getFileSize();
		return fileSize / this.nPart + ((fileSize % this.nPart == 0) ? 0 : 1);
	}
	
}
