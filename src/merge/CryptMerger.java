package merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import split.CryptSplitter;
import split.Splitter;


/**
 * Oggetto che permette di ottenere il file originale unendo tutte
 * le parti generate in precedenza con CryptSplitter a patto che si 
 * trovino tutti nella stessa cartella
 * @author Filippo Altimani
 * @see CryptSplitter
 */
public class CryptMerger extends Merger {
	
	/**
	 * Chiave di decriptazione
	 */
	private String key;
	
	public CryptMerger(String originalFilePath) {
		super(originalFilePath);
	}
	
	
	/**
	 * Imposta la chiave di decriptazione desiderata
	 * @param k La chiave di decriptazione che si vuole inserire
	 */
	public void setKey(String k) {
		this.key = k;
	}
	
	
	/**
	 * Controlla che la chiave key sia della lunghezza esatta
	 */
	@Override
	public String check() {
		String returnMessage = super.check();

		if (this.key.length() != 16) 
			returnMessage += "Lunghezza chiave non valida: deve essere esattamente di 16 caratteri\n";
		
		return returnMessage;
	}
	
	
	public String merge() {
		String returnMessage = this.check();
		int nParts = 1;
		
		if (returnMessage.equals("")) {
			byte[] keyBytes = this.key.getBytes();
			SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
			
			try {
				Cipher decoder = Cipher.getInstance("AES/CBC/PKCS5Padding");
				decoder.init(Cipher.DECRYPT_MODE,secretKey, decoder.getParameters());
				
				try (FileOutputStream out = new FileOutputStream(originalFile)) {
					File part;
					
					while ((part = new File(this.originalFile.getAbsolutePath() + "." + nParts + "." + CryptSplitter.EXTENSION + Splitter.EXTENSION)).canRead() && part.length()>0) {
						try (FileInputStream fin = new FileInputStream(part)) {

							byte[] dataBytes = new byte[2048];
							
							while (fin.read(dataBytes)>0)
								out.write(decoder.doFinal(dataBytes));
						}
					}
				}
			} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
				e.printStackTrace();
				returnMessage = "Errore nell'inizializzazione del dencoder\n";
			} catch (IOException e) {
				e.printStackTrace();
				returnMessage += "Errore I/O";
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				e.printStackTrace();
				returnMessage += "Errore nella decodifica del file";
			}
		}

		return returnMessage;
	}

}
