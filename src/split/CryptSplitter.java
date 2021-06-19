package split;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class CryptSplitter extends DefaultSplitter {

	private String key;
	private Cipher encoder;
	public static String EXTENSION = "cry";
	
	
	public CryptSplitter(String fp) {
		super(fp);
		this.key = "";
	}
	
	
	public CryptSplitter(String fp, int s, String k) {
		super(fp,s);
		this.key = k;
	}
	
	
	public void setKey(String k) {
		this.key = k;
	}
	
	
	public String getKey() {
		return this.key;
	}
	

	public String getExtension() {
		return "." + CryptSplitter.EXTENSION + "." + Splitter.EXTENSION;
	}
	
	
	public String check() {
		String returnMessage = super.check();

		if (this.key.length() != 16) 
			returnMessage += "Lunghezza chiave non valida: deve essere esattamente di 16 caratteri\n";
		
		return returnMessage;
	}
	
	
	protected byte[] processByte(byte[] b) throws Exception {
        return this.encoder.doFinal(b);
	}
	
	
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
