package split;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public abstract class Splitter {
	
	private File file;
	public static String EXTENSION = "par";
	
	
	protected Splitter(String fp) {
		this.setFilePath(fp);
	}
	
	
	public String toString() {
		return "(" + this.getFileSize() + " B) " + this.getFilePath();
	}
	
	public String getExtension() {
		return "." + Splitter.EXTENSION;
	}
	
	
	public void setFilePath(String fp) {
		this.file = new File(fp);
	}
	
	
	public void setFile(File f) {
		this.file = f;
	}
	
	protected File getFile() {
		return this.file;
	}
	
	
	public String getFileName() {
		return this.file.getName();
	}
	
	
	public String getFilePath() {
		return this.file.getAbsolutePath();
	}


	public long getFileSize() {
		return this.file.length();
	}
	
	
	public String check() {
		String returnMessage = "";
		
		if (!file.canRead() || this.getFileSize()<=0)
			returnMessage = "Impossibile leggere il file inserito\n";
		
		return returnMessage;
	}
	
	
	protected byte[] processByte(byte[] b) throws Exception {
		return b;
	}
	
	
	protected void writePart(ArrayList<byte[]> b, File part) throws IOException {
		try (FileOutputStream pfout = new FileOutputStream(part)) {
			for (byte[] by : b)
				pfout.write(by);
		}
	}
	
	
	public abstract long getPartSize(int partsCounter);
	
	
	public String split() {
		String returnMessage =  "!! " + this.getFilePath() + "\n";
		String checkMessage = this.check();
		
		if (!checkMessage.equals(""))
			returnMessage += checkMessage;
			
		else {
			
			long remainingSize = this.getFileSize();
			int partsCounter = 0;
			try (FileInputStream fin = new FileInputStream(this.getFile())){
				
				while (remainingSize > 0) {
					String partPath = this.getFilePath() + "." + (partsCounter+1) + this.getExtension();
					File part = new File(partPath);
					
					long partSize = this.getPartSize(partsCounter);
					if (partSize <= 0)
						partSize = remainingSize;

					long partSizeBuffered = partSize;
					ArrayList<byte[]> buffer = new ArrayList<byte[]>();
					
					while (partSizeBuffered>0) {
						if (partSizeBuffered>Integer.MAX_VALUE) {
							buffer.add(this.processByte(fin.readNBytes(Integer.MAX_VALUE)));
							partSizeBuffered -= Integer.MAX_VALUE;
						}
						
						else {
							buffer.add(this.processByte(fin.readNBytes((int)partSizeBuffered)));
							partSizeBuffered = 0;
						}
					}
					
					this.writePart(buffer, part);
					
					partsCounter++;
					remainingSize -= partSize;
				}
				
				returnMessage = "";
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				returnMessage += "File multimediale non trovato";
			} catch (IOException e) {
				e.printStackTrace();
				returnMessage += "Errore I/O";
			} catch (Exception e) {
				e.printStackTrace();
				returnMessage += "Errore";
			}
			
		}
		
		return returnMessage;
	}
}
