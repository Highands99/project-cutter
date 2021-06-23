package merge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import split.Splitter;


/**
 * Oggetto che permette di ottenere il file originale da una cartella compressa, 
 * contenente le sue parti, creata in precedenza con CompressionSplitter
 * @author Filippo Altimani
 * @see split.CompressionSplitter
 *
 */
public class CompressionMerger extends Merger {
	
	private static final long serialVersionUID = 33L;

	/**
	 * File zip contenente le parti del file originale 
	 */
	private ZipFile zipFile;
	
	/**
	 * Le parti compresse del file originale
	 */
	private Enumeration<? extends ZipEntry> entries;
	
	
	public CompressionMerger(String originalFilePath) {
		super(originalFilePath);
	}
	
	
	/**
	 * Inizializza il file zip
	 */
	@Override
	public void setOriginalFile(String originalFilePath) {
		super.setOriginalFile(originalFilePath);
		
		File zipF = new File(originalFilePath+"."+Splitter.EXTENSION+".zip");
		
		if (zipF.canRead() && zipF.length()>0) {
			try {
				this.zipFile = new ZipFile(zipF);
				this.entries = zipFile.entries();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Controlla che il file zip abbia delle entries
	 */
	@Override
	public String check() {
		String returnMessage = super.check();
		
		if (zipFile.size()<=0 || !entries.hasMoreElements())
			returnMessage += "Errore nell'apertura del file zip\n";
		
		return returnMessage;
	}
	
	
	public String merge() {
		String returnMessage = this.check();
		
		if (returnMessage.equals("")) {
			try (FileOutputStream out = new FileOutputStream(originalFile)){
				
				while (entries.hasMoreElements()) {
					ZipEntry ze	 = entries.nextElement();
					
					try (BufferedInputStream zin = new BufferedInputStream(zipFile.getInputStream(ze))) {
						int nBytes = -1; 
						byte[] buffer = new byte[2048];
						
						while((nBytes = zin.read(buffer)) > 0)
							out.write(buffer, 0, nBytes);
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}

		return returnMessage;
	}

}
