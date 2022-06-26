package etf.unibl.org.file_writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import etf.unibl.org.controllers.UpravljanjeIgracimaController;
import etf.unibl.org.models.Matrica;
import etf.unibl.org.models.Polje;
import etf.unibl.org.models.interfaces.Figura;

public class RezultatFile {
	
	public static FileHandler handler;
	
	static{
		try
		{
			handler = new FileHandler("RezultatFile.log");
			Logger.getLogger(RezultatFile.class.getName()).addHandler(handler);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private String lokacija;
	//= "C:\\Users\\win7\\eclipse-workspace\\DiamondCircle(PJ2)\\src\\igre\\";
	private Matrica mat;
	
	public RezultatFile(Matrica mat)
	{
		this.mat = mat;
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("resources/config/config.properties");
		} catch (FileNotFoundException e) {
			Logger.getLogger(RezultatFile.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		try {
			prop.load(inputStream);
		} catch (IOException e) {
			Logger.getLogger(RezultatFile.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
		this.lokacija = prop.getProperty("rezultati");
		
		try {
			inputStream.close();
		} catch (IOException e) {
			Logger.getLogger(RezultatFile.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
		handler.close();
	}
	
	public void upisiRezultatIgre(String vrijemeIzvrsavanja) throws IOException
	{
		 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
		 LocalDateTime now = LocalDateTime.now();  
		 String opis[] = dtf.format(now).split(" ");
		 opis[1] = opis[1].replace(":", "");
		 //File direktorijum = new File("C:\\Users\\win7\\eclipse-workspace\\DiamondCircle(PJ2)\\src\\igre");
		 File fajlRezultat = new File(lokacija+"IGRA_"+opis[0]+"_"+opis[1] + ".txt");
		 fajlRezultat.createNewFile();
		 //FileOutputStream fos = null;
		 BufferedWriter writer = null;
		 writer = new BufferedWriter(new FileWriter(fajlRezultat, true));
		 
//		 try {
//			fos = new FileOutputStream(fajlRezultat);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
//		 OutputStreamWriter osw = new OutputStreamWriter(fos);
		 
		 for(int i = 0; i < UpravljanjeIgracimaController.listaZavrsenihFigura.size(); i++)
		 {
			 writer.write("Igrac "+String.valueOf(i+1) + " - " + UpravljanjeIgracimaController.listaZavrsenihFigura.get(i).get(0).getIgracVlasnik().getNaziv());
			 writer.newLine();
			 for(int j = 0; j < 4; j++ )
			 {
				 Figura temp = UpravljanjeIgracimaController.listaZavrsenihFigura.get(i).get(j);
				 //System.out.println(i + " " + j);
		
				 String rezultatPutanje = "";
				 for(Polje p:mat.getPutanja())
				 {
					 if(p.getIndexPutanje()<=temp.getIndexPosljednjegPolja())
					 {
						 if(p.getIndexPutanje()!=0)
						 {
							 rezultatPutanje += " - ";
						 }
						 else
						 {
							 rezultatPutanje += "(";
						 }
						 rezultatPutanje += p.getVrijednostPolja().toString();
					 }
					 else
					 {
						 break;
					 } 
				 }
				 rezultatPutanje += ")";
				 String figuraJeStiglaDoKraja = (temp.getZavrsenPut()) ? "da" : "ne";
				 writer.write("Figura " + String.valueOf(j+1) + "("+temp.getOznaka()+")" + " - predjen put: " + rezultatPutanje + " - stigla do kraja: " + figuraJeStiglaDoKraja);
				 writer.newLine();
			 }
			 writer.newLine();
			 writer.newLine();
		 }
		 
		 writer.write("Ukupno vrijeme izvrsavanja je: " + vrijemeIzvrsavanja + "s");
		 writer.close();
	}
}

