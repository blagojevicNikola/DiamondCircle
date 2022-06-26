package etf.unibl.org.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import etf.unibl.org.config.Config;
import etf.unibl.org.models.interfaces.Karta;

public class SpilKarata {
	
	private Integer brojKarata = Config.VELICINA_SPILA;
	private List<Karta> spil = new ArrayList<Karta>();
	
	public SpilKarata()
	{
		for(int i = 1; i <= brojKarata - Config.BROJ_SPEC_KARATA; i++)
		{
			spil.add(new ObicnaKarta((i%Config.MAX_VRIJEDNOST_OBICNE_KARTE) + 1));
		}
		
		for(int i = 0; i < Config.BROJ_SPEC_KARATA; i++)
		{
			spil.add(new SpecijalnaKarta());
		}
		Collections.shuffle(spil);
	}
	
	public Karta izvuciKartu()
	{
		Karta temp = spil.get(0);
		spil.remove(temp);
		spil.add(temp);
		return temp;
	}
	
}
