package split;


public class DefaultSplitter extends Splitter {
	
	private long size;

	
	public DefaultSplitter(String fp) {
		super(fp);
		this.setPartSize(0);
	}
	
	
	public DefaultSplitter(String fp, int s) {
		super(fp);
		this.setPartSize(s);
	}
	
	
	public void setPartSize(int s) {
		if (s>0) 
			this.size = s;
		else {
			long fileSize = this.getFileSize();
			this.size = fileSize /2 + ((fileSize % 2 == 0) ? 0 : 1);
		}
	}
	
	
	public long getPartSize() {
		return this.size;
	}
	
	
	public long getPartSize(int partsCounter) {
		return this.getPartSize();
	}
	

	public String check() {
		String returnMessage = super.check();
		
		if (this.size<=0 || this.size>this.getFileSize())
			returnMessage += "Dimensione scelta non valida\n";
		
		return returnMessage;
	}

}

/*public String split() {

String returnMessage = "!! " + this.getFilePath() + "\n";

if (this.size<=0 || this.size>this.getFileSize())
	returnMessage += "Dimensione scelta non valida";

else {
	long remainingSize = this.getFileSize();
	
	try {
		FileInputStream fin = new FileInputStream(this.getFile());
		int partsCounter = 1;
		
		while (remainingSize > 0) {
			String partPath = this.getFilePath() + partsCounter + this.getExtension();
			File part = new File(partPath);

			FileOutputStream pfout = new FileOutputStream(part);
			byte[] writingBytes = this.processByte(fin.readNBytes((int) this.size));
			pfout.write(writingBytes);
			
			partsCounter++;
			remainingSize -= this.size;
			pfout.close();
		}
		
		fin.close();
		returnMessage = "";
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		returnMessage += "File multimediale non trovato";
	} catch (IOException e) {
		e.printStackTrace();
		returnMessage += "Errore I/O";
	} catch (IllegalBlockSizeException | BadPaddingException e) {
		e.printStackTrace();
		returnMessage += "Errore Cipher";
	} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
		e.printStackTrace();
		returnMessage += "Errore nella generazione della chiave";
	}
}

return returnMessage;
}*/