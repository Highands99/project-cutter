package model;

import java.io.File;
import java.util.ArrayList;

public abstract class Merger {
	private File inputFile;
	private ArrayList parts;
	
	
	protected Merger(String fp) {
		this.setFilePath(fp);
	}
	
	
	public void setFilePath(String fp) {
		this.inputFile = new File(fp);
	}
	
	
	protected boolean findAll() {
		boolean returnFlag = false;
		if (this.inputFile.length()>0 && this.inputFile.canRead()) {
			String fileName = this.inputFile.getName();
			String[] fileNameParts = fileName.split(".");
			if (fileNameParts[fileNameParts.length-1].equals("zip") && fileNameParts[fileNameParts.length-2].equals(Splitter.EXTENSION)) {
				//se è zippato...
			}
			
			else if (fileNameParts[fileNameParts.length-1].equals(Splitter.EXTENSION)) {
				if (fileNameParts[fileNameParts.length-2].equals(CryptSplitter.EXTENSION)) {
					//se è criptato...
				}
				
				else {
					//se è standard...
				}
			}
		}
		
		return returnFlag;
	}
	
	public String Merge() {
		return "";
	}
}
