package etf.unibl.org.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import etf.unibl.org.models.interfaces.Karta;

public class SpilKarata {
	
	private Integer brojKarata = 52;
	private List<Karta> spil = new ArrayList<Karta>();
	
	public SpilKarata()
	{
		for(int i = 1; i <= brojKarata - 12; i++)
		{
			spil.add(new ObicnaKarta((i%4) + 1));
		}
		
		for(int i = 0; i < 12; i++)
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
