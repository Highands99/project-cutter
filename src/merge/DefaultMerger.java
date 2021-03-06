package merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import split.Splitter;


/**
 * Oggetto che permette di ottenere il file originale unendo tutte
 * le parti generate in precedenza con DefaultSplitter o NPartSplitter a patto che si 
 * trovino tutti nella stessa cartella
 * @author Filippo Altimani
 * @see split.DefaultSplitter
 * @see split.NPartSplitter
 */
public class DefaultMerger extends Merger {
	
	public DefaultMerger(String originalFilePath) {
		super(originalFilePath);
	}
	
	
	public String merge() {
		String returnMessage = super.check();
		int nParts = 1;
		
		if (returnMessage.equals("")) {
			try (FileOutputStream out = new FileOutputStream(originalFile)) {
				File part;
				
				while ((part = new File(this.originalFile.getAbsolutePath() + "." + nParts + "." + Splitter.EXTENSION)).canRead() && part.length()>0) {
					try (FileInputStream fin = new FileInputStream(part)) {
						byte[] dataBytes;
						
						while ((dataBytes=fin.readAllBytes()).length>0)
							out.write(dataBytes);
					}
					nParts++;
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				returnMessage += "Errore I/O";
			}
		}
		
		return returnMessage;
	}

}
