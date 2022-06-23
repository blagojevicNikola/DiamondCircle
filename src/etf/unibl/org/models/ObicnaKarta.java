package etf.unibl.org.models;

import etf.unibl.org.models.interfaces.Karta;
import etf.unibl.org.models.interfaces.ObicnaKartaInterface;

public class ObicnaKarta extends Karta implements ObicnaKartaInterface {
	
	
	private Integer broj;

	public ObicnaKarta(Integer br)
	{
		super("src/application/images.png");
		broj = br;
	}
	
	public Integer getBroj() {
		return broj;
	}
	
	public void setBroj(Integer broj) {
		this.broj = broj;
	}
	
	
}
