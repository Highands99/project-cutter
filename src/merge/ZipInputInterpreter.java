package merge;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipInputInterpreter extends InputInterpreter {
	
	ZipInputStream zipFin;
	
	public ZipInputInterpreter(String filePath) {
		File zipFile = new File(filePath);
		
		if (zipFile.canRead() && zipFile.length()>0) {
			try {
				FileInputStream fin = new FileInputStream(zipFile);
				this.zipFin = new ZipInputStream(fin);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	public byte[] getNextPart() {
		byte[] returnBytes = null;
		
		try {
			ZipEntry entry = this.zipFin.getNextEntry();
			
			if (entry != null) {
				//this.zipFin.re
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}


		
		return returnBytes;
	}

}
