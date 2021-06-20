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
