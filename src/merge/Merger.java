package merge;

import split.CryptSplitter;
import split.Splitter;

public class Merger {
	
	private InputInterpreter interpreter;
	private String originalFilePath = "";
	
	
	protected Merger(String filePath) {
		this.setInterpreter(filePath);
	}
	
	
	public void setInterpreter(String filePath) {
	
		String[] fileNameParts = filePath.split(".");
		
		if (fileNameParts[fileNameParts.length-1].equals("zip") && fileNameParts[fileNameParts.length-2].equals(Splitter.EXTENSION)) {
			this.interpreter = new ZipInputInterpreter(filePath);
			this.setOriginalFilePath(fileNameParts, fileNameParts.length-2);
		}
		
		else if (fileNameParts[fileNameParts.length-1].equals(Splitter.EXTENSION)) {
			if (fileNameParts[fileNameParts.length-2].equals(CryptSplitter.EXTENSION)) {
				this.setOriginalFilePath(fileNameParts, fileNameParts.length-3);
				this.interpreter = new CryptInputInterpreter(originalFilePath);
			}
			
			else {
				this.setOriginalFilePath(fileNameParts, fileNameParts.length-2);
				this.interpreter = new DefaultInputInterpreter(this.originalFilePath);
			}
		}

	}
	
	
	private void setOriginalFilePath(String[] path, int maxIndex) {
		this.originalFilePath.concat(path[0]);
		for (int i=1; i<maxIndex;i++)
			this.originalFilePath.concat("." + path[i]);
	}
	
	
	protected boolean check() {
		boolean returnFlag = false;
		
		return returnFlag;
	}
	
	public String Merge() {
		return "";
	}
}
