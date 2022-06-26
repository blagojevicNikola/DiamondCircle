package etf.unibl.org.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import etf.unibl.org.models.interfaces.Karta;
import etf.unibl.org.models.interfaces.SpecijalnaKartaInterface;

public class SpecijalnaKarta extends Karta implements SpecijalnaKartaInterface{

	private List<Integer> pozicijeCrnihRupa = new ArrayList<Integer>();
	
	
	public static FileHandler handler;
	
	static{
		try
		{
			Properties prop = new Properties();
			InputStream inputStream = new FileInputStream("resources/config/config.properties");
			prop.load(inputStream);
			handler = new FileHandler(prop.getProperty("logFolder") + "SpecijalnaKarta.log");
			Logger.getLogger(SpecijalnaKarta.class.getName()).addHandler(handler);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public SpecijalnaKarta() {
		super();
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("resources/config/config.properties");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(SpecijalnaKarta.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		try {
			prop.load(inputStream);
		} catch (IOException e1) {
			Logger.getLogger(SpecijalnaKarta.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
		
		
		Random rand = new Random();
		Integer brojCrnihRupa = rand.nextInt(Integer.valueOf(prop.getProperty("maxBrojCrnihRupa")))+1;
		
		int i = 0;
		while(i<brojCrnihRupa)
		{
			Integer p = rand.nextInt(Matrica.putanja.size());
			if(!pozicijeCrnihRupa.contains(p))
			{
				pozicijeCrnihRupa.add(p);
				i++;
			}
		}
		
		handler.close();
	}

	@Override
	public void postaviCrneRupe(Matrica matrica) {
		
			
			matrica.postaviCrneRupe(pozicijeCrnihRupa);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Logger.getLogger(SpecijalnaKarta.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			
			matrica.ukloniCrneRupe(pozicijeCrnihRupa);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Logger.getLogger(SpecijalnaKarta.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			
			handler.close();
		}
	
	public Integer getBrojCrnihRupa()
	{
		return this.pozicijeCrnihRupa.size();
	}
	
	public String getOpisKarte(Matrica mat)
	{
		String pozicije = "";
		for(int i = 0; i < this.pozicijeCrnihRupa.size(); i++)
		{
			if(i!=0)
			{
				pozicije+=",";
			}
			pozicije+=mat.getPutanja().get(pozicijeCrnihRupa.get(i)).getVrijednostPolja().toString();
		}
		return pozicije;
	}
}
	

	

