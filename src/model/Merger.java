package model;

import java.io.File;

public abstract class Merger {
	private File file;
	
	
	protected Merger(String fp) {
		this.setFilePath(fp);
	}
	
	
	public void setFilePath(String fp) {
		this.file = new File(fp);
	}
	
	
	public long getFileSize() {
		return this.file.length();
	}
	
	
	public String getFileName() {
		return this.file.getName();
	}
	
	
	public String check() {
		String returnMessage = "";
		
		if (!file.canRead() || this.getFileSize()<=0)
			returnMessage = "Impossibile leggere il file inserito\n";
		
		return returnMessage;
	}
	
	
	public String Merge() {
		return "";
	}
}
