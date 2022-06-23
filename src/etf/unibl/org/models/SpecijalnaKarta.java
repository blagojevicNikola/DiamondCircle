package etf.unibl.org.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import etf.unibl.org.models.interfaces.Karta;
import etf.unibl.org.models.interfaces.SpecijalnaKartaInterface;

public class SpecijalnaKarta extends Karta implements SpecijalnaKartaInterface{

	private List<Integer> pozicijeCrnihRupa = new ArrayList<Integer>();
	
	public SpecijalnaKarta() {
		super("src/application/images.png");
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("resources/config/config.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			prop.load(inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Random rand = new Random();
		Integer brojCrnihRupa = rand.nextInt(Integer.valueOf(prop.getProperty("maxBrojCrnihRupa")))+1;
		
		int i = 0;
		while(i<brojCrnihRupa)
		{
			Integer p = rand.nextInt(25);
			if(!pozicijeCrnihRupa.contains(p))
			{
				pozicijeCrnihRupa.add(p);
				i++;
			}
		}
	}

	@Override
	public void postaviCrneRupe(Matrica matrica) {
		
			
			matrica.postaviCrneRupe(pozicijeCrnihRupa);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			matrica.ukloniCrneRupe(pozicijeCrnihRupa);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	

	

