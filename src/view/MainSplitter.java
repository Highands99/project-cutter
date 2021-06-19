package view;

/*import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import model.*;*/

public class MainSplitter {

	public static void main(String[] args) {
		JFrmMain mainF = new JFrmMain();
		
		mainF.setResizable(false);
		mainF.setLocationRelativeTo(null);
			
		mainF.setVisible(true);
		
		/*lol a = new lol();
		lel b = new lel("hoi");
		
		System.out.println("lol nel main: " + a.hola());
		System.out.println("lel nel main: " + b.hola());*/
		
		/*byte[] keyBytes = "ciao".getBytes();
		SecretKeySpec k = new SecretKeySpec(keyBytes, "AES");

		System.out.println(k);*/
		
		/*ArrayList<Integer> a = new ArrayList<Integer>();
		a.add(12321);
		a.add(3);
		for(int i : a)
			System.out.println(i);*/
		
		/*Splitter a = new CompressionSplitter("C:\\Users\\Filippo\\Desktop\\javaTest\\testFile.ods");
		CompressionSplitter b = (CompressionSplitter) a;
		Vector<Integer> i = new Vector<Integer>();
		i.add(1000);
		i.add(13000);
		i.add(895);
		b.setPartsList(i);
		a = b;
		System.out.println(a.split());*/
		/*try {
			
			File all = new File("C:\\Users\\Filippo\\Desktop\\javaTest\\testFile.ods");
			File part = new File("C:\\Users\\Filippo\\Desktop\\javaTest\\testFile.ods.part.zip");
			ZipOutputStream pfout = new ZipOutputStream(new FileOutputStream(part));
			FileInputStream fin = new FileInputStream(all);
			pfout.putNextEntry(new ZipEntry(all.getName()));
			pfout.write(fin.readAllBytes());
			pfout.close();
			fin.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

}
