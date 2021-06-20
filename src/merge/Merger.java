package merge;

import java.io.File;

public abstract class Merger {
	protected File originalFile;
	
	protected Merger (String originalFilePath) {
		this.setOriginalFile(originalFilePath);
	}
	
	public void setOriginalFile(String originalFilePath) {
		this.originalFile = new File(originalFilePath);
	}
	
	public String check() {
		String returnMessage = "";
		
		if (this.originalFile.exists())
			returnMessage = "File originale già presente, impossibile effettuare la ricomposizione\n";
		
		return returnMessage;
	}
	
	public abstract String merge();
}
