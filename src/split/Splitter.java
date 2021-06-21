package split;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;


/**
 * Classe astratta per dividere un file in più parti 
 * @author Filippo Altimani
 *
 */
public abstract class Splitter {
	
	/**
	 * File da dividere
	 */
	private File file;
	
	/**
	 * Stringa che viene aggiunta in coda al nome dei file contenenti le parti del file originale
	 */
	public static final String EXTENSION = "par";
	
	
	/**
	 * Costruisce a partire dal percorso del file da dividere
	 * @param fp Percorso del file da dividere
	 */
	protected Splitter(String fp) {
		this.setFilePath(fp);
	}
	
	
	/**
	 * "([Dimensione]B) [percorso]" del file originale
	 */
	public String toString() {
		return "(" + this.getFileSize() + " B) " + this.getFilePath();
	}
	
	
	/**
	 * Aggiunge il punto all'attributo EXTENSION
	 * @return "." + EXTENSION
	 */
	public String getExtension() {
		return "." + Splitter.EXTENSION;
	}
	
	
	/**
	 * Inizilizza il file attraverso il percorso 
	 * @param fp Percorso del file da dividere
	 */
	public void setFilePath(String fp) {
		this.file = new File(fp);
	}
	
	
	/**
	 * Inizilizza il file attraverso un oggetto File
	 * @param f File da dividere
	 */
	public void setFile(File f) {
		this.file = f;
	}
	
	
	/**
	 * Ritorna il file da dividere
	 * @return File da dividere
	 */
	protected File getFile() {
		return this.file;
	}
	
	
	/**
	 * Ritorna il nome del file da dividere
	 * @return Nome del file da dividere
	 */
	public String getFileName() {
		return this.file.getName();
	}
	
	
	/**
	 * Ritorna il percorso del file da dividere
	 * @return Il percorso del file da dividere
	 */
	public String getFilePath() {
		return this.file.getAbsolutePath();
	}

	
	/**
	 * Ritorna la dimensione del file da dividere
	 * @return La dimensione del file da dividere
	 */
	public long getFileSize() {
		return this.file.length();
	}
	
	
	/**
	 * Controlla che il file possa essere diviso: che sia leggibile e abbia del contenuto
	 * @return Una stringa con il messaggio di errore oppure una stringa vuota ""
	 */
	public String check() {
		String returnMessage = "";
		
		if (!file.canRead() || this.getFileSize()<=0)
			returnMessage = "Impossibile leggere il file inserito\n";
		
		return returnMessage;
	}
	
	
	/**
	 * Filtra i byte passati come parametro.  
	 * @param b Byte da filtrare
	 * @return Byte filtrati
	 * @throws IllegalBlockSizeException Questa eccezione viene sollevata da un overload del metodo effettuato dalla classe CryptoSplitter
	 * @throws BadPaddingException Questa eccezione viene sollevata da un overload del metodo effettuato dalla classe CryptoSplitter
	 * @see CryptoSplitter
	 */
	protected byte[] processByte(byte[] b) throws IllegalBlockSizeException, BadPaddingException {
		return b;
	}
	
	
	/**
	 * Crea il un file contenente i byte passati come parametri
	 * @param b Byte che si voglio inserire nel file
	 * @param part File su cui si vogliono scrivere i byte
	 * @throws IOException Questa eccezione viene sollevata dal FileInputStream
	 */
	protected void writePart(ArrayList<byte[]> b, File part) throws IOException {
		try (FileOutputStream pfout = new FileOutputStream(part)) {
			for (byte[] by : b)
				pfout.write(by);
		}
	}
	
	
	/**
	 * Restituisce la dimensione della/e parte/i in cui suddividere il file
	 * @return La dimensione della/e parte/i
	 */
	public abstract long getPartSize();
	
	
	/**
	 * Dividi il file in più parti
	 * @return Una stringa con il messaggio di errore oppure una stringa vuota ""
	 */
	public String split() {
		String fileInfoMessage =  "!! " + this.getFilePath() + "\n";
		String returnMessage = fileInfoMessage + this.check();
		
		if (returnMessage.equals(fileInfoMessage)) {
			
			long remainingSize = this.getFileSize();
			int partsCounter = 0;
			try (FileInputStream fin = new FileInputStream(this.getFile())){
				
				while (remainingSize > 0) {
					String partPath = this.getFilePath() + "." + (partsCounter+1) + this.getExtension();
					File part = new File(partPath);
					
					long partSize = this.getPartSize();
					if (partSize <= 0)
						partSize = remainingSize;

					long partSizeBuffered = partSize;
					ArrayList<byte[]> buffer = new ArrayList<>();
					
					while (partSizeBuffered>0) {
						int sizeRead = Integer.MAX_VALUE;
						
						if (partSizeBuffered<Integer.MAX_VALUE)
							sizeRead = (int)partSizeBuffered;
		
						buffer.add(this.processByte(fin.readNBytes(sizeRead)));
						partSizeBuffered -= sizeRead;
					}
					
					this.writePart(buffer, part);
					
					partsCounter++;
					remainingSize -= partSize;
				}
				
				returnMessage = "";
				
			} catch (IOException e) {
				e.printStackTrace();
				returnMessage += "Errore I/O";

			} catch (IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
				returnMessage += "Errore di criptazione";
			}
			
		}
		
		return returnMessage;
	}
}
