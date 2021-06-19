package model;


public class NPartSplitter extends Splitter {
	
	private int nPart;
	
	
	public NPartSplitter(String fp) {
		super(fp);
		this.nPart = 2;
	}
	
	
	public NPartSplitter(String fp, int np) {
		super(fp);
		this.setNPart(np);
	}
	

	public int getNPart() {
		return nPart;
	}


	public void setNPart(int np) {
		if (np>1)
			this.nPart = np;
		else
			this.nPart = 2;
	}
	
	
	public String check() {
		String returnMessage = super.check();
		
		if(this.nPart<2 || this.nPart>this.getFileSize())
			returnMessage += "Numero di parti errato\n";
		
		return returnMessage;
	}
	
	
	public long getPartSize() {
		long fileSize = this.getFileSize();
		return fileSize / this.nPart + ((fileSize % this.nPart == 0) ? 0 : 1);
	}
	
	
	public long getPartSize(int partCounter) {
		return this.getPartSize();
	}
}
