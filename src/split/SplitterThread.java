package split;

import javax.swing.JProgressBar;

public class SplitterThread extends Thread{
	
	private Splitter splitter;
	private StringBuffer  ErrorMessage;
	JProgressBar progressBar;
	
	
	
	public SplitterThread(Splitter s, StringBuffer  em, JProgressBar pbc) {
		this.splitter = s;
		this.ErrorMessage = em;
		this.progressBar = pbc;
	}
	
	
	public void run() {
		String returnMeassage = this.splitter.split();
		if (!returnMeassage.equals("")) 
			ErrorMessage.append(returnMeassage+"\n");
		synchronized(progressBar) {
			this.progressBar.setValue(progressBar.getValue()+1);
		}
	}
}	
	
