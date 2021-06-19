package split;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressionSplitter extends Splitter {
	
	private Vector<Long> partsSize;
	private ZipOutputStream zipFolder;
	
	
	public CompressionSplitter(String fp) {
		super(fp);
		this.partsSize = new Vector<Long>();
	}
	
	
	public Vector<Long> getPartsList() {
		return this.partsSize;
	}
	
	
	public void setPartsList(Vector<Long> pl) {
		this.partsSize.removeAllElements(); 
		this.partsSize = pl;
	}
	
	
	public String check() {
		String returnMessage = super.check();
		int totalSize = 0;
		
		if (this.partsSize.size()>0) {
			for (long part : partsSize) {
				if (part<=0)
					returnMessage += "Valore non valido: " + part + "\n";
				totalSize += part;
			}
			
			if (totalSize > this.getFileSize() || totalSize == 0)
				returnMessage += "Dimensioni inserite non valide\n";
			
		} else 
			returnMessage += "Dimensione/i mancante/i\n";
		
		return returnMessage;
	}
	
	
	public long getPartSize(int partsCounter) {
		long returnInt = 0;
		if (partsCounter < this.partsSize.size())
			returnInt = this.partsSize.get(partsCounter);
		return returnInt;
	}
	
	
	protected void writePart(byte[] b, File part) throws IOException {
		zipFolder.putNextEntry(new ZipEntry(part.getName()));
		zipFolder.write(b);
		zipFolder.closeEntry();
	}
	
	
	public String split() {
		String returnMessage = "";
		try {
			File folder = new File(this.getFilePath()+Splitter.EXTENSION+".zip");
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
