package split;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Oggetto che consente la divisione in più parti specificando
 * la dimensione di ogni parte e comprimendo il contenuto dei file 
 * generati
 * @author Filippo Altimani
 * 
 */
public class CompressionSplitter extends Splitter {
	
	private static final long serialVersionUID = 24L;

	/**
	 *  Lista di dimensioni in byte per ogni parte
	 */
	private List<Long> partsSize;
	
	/**
	 * Oggetto per la gestione dell'output compresso
	 */
	private ZipOutputStream zipFolder;
	
	/**
	 * Indice per accedere alla lista delle dimensioni
	 * @see #getPartSize()
	 */
	private int partsIndex;
	
	
	/**
	 * Costruisce a partire dal percorso del file da dividere, inizializza la 
	 * lista delle dimensioni vuota e imposta a 0 l'indice per accedere alla 
	 * lista delle dimensioni
	 * @param fp Percorso del file da dividere
	 */
	public CompressionSplitter(String fp) {
		super(fp);
		this.partsSize = new ArrayList<>();
		this.partsIndex = 0;
	}
	
	
	/**
	 * Ritorna la lista di dimensioni per ogni parte
	 * @return Lista di dimensioni per ogni parte
	 */
	public List<Long> getPartsList() {
		return this.partsSize;
	}
	
	
	/**
	 * Imposta la lista di dimensioni per ogni parte
	 * @param pl Lista di dimensioni per ogni parte
	 */
	public void setPartsList(List<Long> pl) {
		this.partsSize.clear(); 
		this.partsSize = pl;
	}
	
	
	/**
	 * Imposta l'indice per accedere alla lista delle dimensioni, se
	 * il parametro in ingresso e' minore di 0 viene impostato a 0
	 * @param pc Indice per accedere alla lista delle dimensioni
	 */
	public void setPartsIndex(int pc) {
		if (pc < 0)
			pc = 0;
		this.partsIndex = pc;
	}
	
	
	/**
	 * Ritorna l'indice per accedere alla lista delle dimensioni
	 * @return Indice per accedere alla lista delle dimensioni
	 */
	public int getPartsIndex() {
		return this.partsIndex;
	}
	
	
	/**
	 * Inserisce in coda una dimensione nella lista di dimensioni
	 * per ogni parte
	 * @param size Dimensione da inserire nella lista
	 */
	public void setPartSize(long size) {
		if (size>0)
			this.partsSize.add(size);
	}
	
	
	/**
	 * Restituisce il valore della dimensione all'indice {@link #partsIndex} attuale ed incrementa di
	 * 1 l'idice. Se l'indice supera la dimensione della lista delle dimensioni, ritorna zero
	 * @return La dimensione all'indice {@link #partsIndex} oppure zero
	 */
	public long getPartSize() {
		long returnInt = 0;
		if (this.partsIndex < this.partsSize.size())
			returnInt = this.partsSize.get(this.partsIndex);
		this.partsIndex++;
		return returnInt;
	}
	
	
	/**
	 * Restituisce il valore della dimensione all'idice in input, se 'indice in 
	 * input supera la dimensione della lista delle dimensioni, ritorna zero
	 * @param pc Indice della lista delle dimensioni
	 * @return La dimensione all'indice indicato oppure zero
	 */
	public long getPartSize(int pc) {
		long returnInt = 0;
		if (pc < this.partsSize.size())
			returnInt = this.partsSize.get(pc);

		return returnInt;
	}
	
	
	/**
	 * Controlla che ogni dimensione nella lista delle dimensioni sia maggiore di zero e 
	 * che la somma delle dimensioni non superi la dimensione totale del file
	 * da dividere
	 */
	@Override
	public String check() {
		StringBuilder returnMessage = new StringBuilder();
		returnMessage.append(super.check());
		int totalSize = 0;
		
		if (!this.partsSize.isEmpty()) {
			for (long part : partsSize) {
				if (part<=0)
					returnMessage.append("Valore non valido: " + part + "\n");
				totalSize += part;
			}
			
			if (totalSize > this.getFileSize() || totalSize == 0)
				returnMessage.append("Dimensioni inserite non valide\n");
			
		} else 
			returnMessage.append("Dimensione/i mancante/i\n");
		
		return returnMessage.toString();
	}
	
	
	/**
	 * Crea all'interno del file zip una nuova entry, contenente una parte del file da dividere
	 */
	@Override
	protected void writePart(ArrayList<byte[]> b, File part) throws IOException {
		zipFolder.putNextEntry(new ZipEntry(part.getName()));
		for (byte[] by : b)
			zipFolder.write(by);
		zipFolder.closeEntry();
	}
	
	
	/**
	 * Inizializza il file zip che andrà a contenere le parti del file da dividere 
	 */
	@Override
	public String split() {
		String returnMessage = "";
		try {
			File folder = new File(this.getFilePath()+"."+Splitter.EXTENSION+".zip");
			zipFolder = new ZipOutputStream(new FileOutputStream(folder));
			returnMessage = super.split();
			zipFolder.close();
		} catch (IOException e) {
			e.printStackTrace();
			returnMessage = "Errore imprevisto nella creazione della cartella zip\n";
		}
		
		return returnMessage;
	}

}
