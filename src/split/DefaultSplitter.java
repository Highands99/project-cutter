package split;

/**
 * Oggetto che consente la divisione in più parti di un file
 * specificando la dimensione uguale per ogni parte
 * @author Filippo Altimani
 *
 */
public class DefaultSplitter extends Splitter {
	
	private static final long serialVersionUID = 22L;
	
	/**
	 * Dimensione in byte uguale per ogni parte
	 */
	private long size;

	
	/**
	 * Costruisce a partire dal percorso del file da dividere e imposta la dimensione 
	 * uguale alla metà della dimensione del file da dividere
	 * @param fp Percorso del file da dividere
	 */
	public DefaultSplitter(String fp) {
		super(fp);
		this.setPartSize(0);
	}
	
	
	/**
	 * Costruisce a partire dal percorso del file da dividere e imposta la dimensione 
	 * @param fp Percorso del file da dividere
	 * @param s Dimensione uguale per ogni parte
	 */
	public DefaultSplitter(String fp, long s) {
		super(fp);
		this.setPartSize(s);
	}
	
	
	/**
	 * Imposta la dimensione uguale per ogni parte, se minore o uguale a 0
	 * viene impostata uguale alla metà della dimensione del file da dividere
	 * @param s Dimensione uguale per ogni parte
	 */
	public void setPartSize(long s) {
		if (s>0) 
			this.size = s;
		else {
			long fileSize = this.getFileSize();
			this.size = fileSize /2 + ((fileSize % 2 == 0) ? 0 : 1);
		}
	}
	
	
	/**
	 * Ritorna la dimensione uguale per ogni parte
	 */
	public long getPartSize() {
		return this.size;
	}
	
	
	/**
	 * Controlla che la dimensione uguale per ogni parte sia corretta
	 */
	@Override
	public String check() {
		String returnMessage = super.check();
		
		if (this.size<=0 || this.size>this.getFileSize())
			returnMessage += "Dimensione scelta non valida\n";
		
		return returnMessage;
	}

}
