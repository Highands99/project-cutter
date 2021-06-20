package merge;

import split.CryptSplitter;
import split.Splitter;

public class FileInterpreter {
	
	private Merger merger;
	private String originalFilePath = "";
	
	
	public FileInterpreter(String filePath) {
		this.setMerger(filePath);
	}
	
	
	public void setMerger(String filePath) {
	
		String[] fileNameParts = filePath.split(".");
		
		if (fileNameParts[fileNameParts.length-1].equals("zip") && fileNameParts[fileNameParts.length-2].equals(Splitter.EXTENSION)) {
			this.setOriginalFilePath(fileNameParts, fileNameParts.length-2);
			this.merger = new CompressionMerger(originalFilePath);
		}
		
		else if (fileNameParts[fileNameParts.length-1].equals(Splitter.EXTENSION)) {
			if (fileNameParts[fileNameParts.length-2].equals(CryptSplitter.EXTENSION)) {
				this.setOriginalFilePath(fileNameParts, fileNameParts.length-3);
				this.merger = new CryptMerger(originalFilePath);
			}
			
			else {
				this.setOriginalFilePath(fileNameParts, fileNameParts.length-2);
				this.merger = new DefaultMerger(this.originalFilePath);
			}
		}

	}
	
	
	public void setPassword(String psw) {
		if (merger instanceof CryptMerger)
			((CryptMerger) merger).setKey(psw);
	}
	
	
	private void setOriginalFilePath(String[] path, int maxIndex) {
		this.originalFilePath = this.originalFilePath.concat(path[0]);
		for (int i=1; i<maxIndex;i++)
			this.originalFilePath = this.originalFilePath.concat("." + path[i]);
	}
	
	
	public String check() {
		return this.merger.check();
	}
	
	public String merge() {
		return this.merger.merge();
	}
}
