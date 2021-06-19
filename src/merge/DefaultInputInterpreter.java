package merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import split.Splitter;

public class DefaultInputInterpreter extends InputInterpreter {
	
	private String headPath;
	private int nParts;
	
	public DefaultInputInterpreter(String originalFilePath) {
		this.nParts = 0;
		this.headPath = originalFilePath;
	}
	
	
	public void setNPart(int i) {
		this.nParts = i;
	}
	
	
	public byte[] getNextPart() {
		byte[] returnBytes = null;
		
		this.nParts++;
		File part = new File(this.headPath + "." + this.nParts + "." + Splitter.EXTENSION);
		
		if (part.canRead() && part.length()>0)
			try (FileInputStream fin = new FileInputStream(part)) {
				returnBytes = fin.readAllBytes();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return returnBytes;
	}

}
