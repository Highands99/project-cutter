package split;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * Oggetto che consente la divisione in più parti di un file
 * specificando la dimensione uguale per ogni parte e crittogrfando
 * il contenuto dei file generati tramite una chiave (la stessa per ogni file)
 * @author Filippo Altimani
 *
 */
public class CryptSplitter extends DefaultSplitter {

	/**
	 * Chiave per la codifica AES dei byte di ogni parte
	 */
	private String key;
	
	/**
	 * Oggetto per codificare i byte del file originale
	 */
	private Cipher encoder;
	
	/**
	 * Stringa che viene aggiunta in coda al nome dei file contenenti le parti del file originale,
	 * prima dell'estensione di Splitter
	 */
	public static final String EXTENSION = "cry";
	
	
	/**
	 * Costruisce a partire dal percorso del file da dividere, imposta la dimensione 
	 * uguale alla metà della dimensione del file da dividere e imposto una stringa vuota per
	 * la chiave
	 * @param fp Percorso del file da dividere
	 */
	public CryptSplitter(String fp) {
		super(fp);
		this.key = "";
	}
	
	
	/**
	 * Costruisce a partire dal percorso del file da dividere, imposta la dimensione e la chiave
	 * @param fp Percorso del file da dividere
	 * @param s Dimensione uguale per ogni parte
	 * @param k Chiave per la codifica
	 */
	public CryptSplitter(String fp, int s, String k) {
		super(fp,s);
		this.key = k;
	}
	
	
	/**
	 * Imposta la chiave di criptazione
	 * @param k Chiave per la codifica
	 */
	public void setKey(String k) {
		this.key = k;
	}
	
	
	/**
	 * Restituisce la chiave di criptazione
	 * @return Chiave per la codifica
	 */
	public String getKey() {
		return this.key;
	}
	
	
	@Override
	public String getExtension() {
		return "." + CryptSplitter.EXTENSION + "." + Splitter.EXTENSION;
	}
	
	
	/**
	 * Verifica che la chiave sia esattamente di 16 caratteri
	 */
	@Override
	public String check() {
		String returnMessage = super.check();

		if (this.key.length() != 16) 
			returnMessage += "Lunghezza chiave non valida: deve essere esattamente di 16 caratteri\n";
		
		return returnMessage;
	}
	
	
	/**
	 * Filtra i byte in ingresso codificandoli con la chiave
	 * @throws IllegalBlockSizeException sollevata dall'encoder di tipo Chiper
	 * @throws BadPaddingException sollevata dall'encoder di tipo Chiper
	 */
	@Override
	protected byte[] processByte(byte[] b) throws IllegalBlockSizeException, BadPaddingException {
        return this.encoder.doFinal(b);
	}
	
	
	/**
	 * Inizializzo l'encoder
	 */
	@Override
	public String split() {
		String returnMessage = "";
		
		byte[] keyBytes = this.key.getBytes();
		SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
		
		try {
			this.encoder = Cipher.getInstance("AES/CBC/PKCS5Padding");
			this.encoder.init(Cipher.ENCRYPT_MODE, secretKey);
			returnMessage = super.split();
			
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
			returnMessage = "Errore nell'inizializzazione dell'encoder\n";
		}
		
		return returnMessage;
	}
}
