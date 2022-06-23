package etf.unibl.org.models.interfaces;

import etf.unibl.org.views.KartaWidget;

public abstract class Karta {
	
	
	private String putanjaSlike;
	
	public Karta(String putanja)
	{
		putanjaSlike = putanja;
	}
	
	public String getPutanjaSlike()
	{
		return putanjaSlike;
	}
}
