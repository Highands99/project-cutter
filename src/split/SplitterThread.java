package split;

import javax.swing.JProgressBar;


/**
 * Oggetto per creare un thread in grado di effettuare la divisione
 * di un file e successivamente di aggiornare una JProgressBar e 
 * di inserire un stringe in cas di errore in un StringBuffer
 * @author Filippo Altimani
 *
 */
public class SplitterThread extends Thread{
	
	private Splitter splitter;
	private StringBuffer errorMessage;
	JProgressBar progressBar;
	
	
	
	public SplitterThread(Splitter s, StringBuffer  em, JProgressBar pbc) {
		this.splitter = s;
		this.errorMessage = em;
		this.progressBar = pbc;
	}
	
	
	@Override
	public void run() {
			
		if (this.splitter != null) {
			String returnMeassage = this.splitter.split();
			
			if (!returnMeassage.equals("")) 
				errorMessage.append(returnMeassage+"\n");
			
			synchronized(progressBar) {
				this.progressBar.setValue(progressBar.getValue()+1);
			}
		}
		
		else
			errorMessage.append("Errore di Inizializzazione dello splitter. Thread id: "+this.getId()+"\n");
	}
	
}	
	
