package etf.unibl.org.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import etf.unibl.org.enums.Boje;
import etf.unibl.org.models.interfaces.Figura;
import javafx.application.Platform;
import javafx.scene.paint.Color;

public class ObicnaFigura extends Figura{

	public ObicnaFigura(Matrica m, String oznaka, Color enumBoja, Igrac igrac) {
		super(m, oznaka, enumBoja, igrac);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		//ObicnaFigura temp = this;
		
		if(this.getIndexPredjenihPolja().size()==0)
		{
			mat.postaviFiguruNaPocetak(this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Integer> poljaZaPrelazak = this.getListaPoljaZaPrelazak();
		
		if(poljaZaPrelazak!=null)
		{
		for (int i = 0; i < poljaZaPrelazak.size()-1; i++) {
			
			mat.ukloniFiguru(this, poljaZaPrelazak.get(i));
			mat.postaviFiguru(this, poljaZaPrelazak.get(i+1));
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(this.getIndexPosljednjegPolja()==mat.getPutanja().get(mat.getPutanja().size()-1).getIndexPutanje())
			{
				System.out.println("To je: " + mat.getPutanja().get(mat.getPutanja().size()-1).getIndexPutanje());
				mat.ukloniFiguru(this, this.getIndexPosljednjegPolja());
				this.setZavrsenPut(true);
				break;
			}
		}
		}
		else
		{
			
		}
	}

	@Override
	public List<Integer> getListaPoljaZaPrelazak()  
	{

		Integer cilj = this.brZadanihPolja + this.getIndexPosljednjegPolja();

		
		if(cilj>=mat.getDuzinaPutanje()-1)
		{
			this.indexCilja = mat.getDuzinaPutanje()-1;
			this.brZadanihPolja = mat.getDuzinaPutanje()-1 - this.getIndexPosljednjegPolja();
			
			return this.popuniListu(this.getIndexPosljednjegPolja(), this.getIndexCilja());
		}
		else
		{
			while(cilj<mat.getDuzinaPutanje()-1)
			{
				if(mat.getPutanja().get(cilj).getFigura()==null)
				{
					this.indexCilja = cilj;
					this.brZadanihPolja = cilj - this.getIndexPosljednjegPolja();
					
					return this.popuniListu(this.getIndexPosljednjegPolja(), this.getIndexCilja());
				}
				else
				{
					cilj++;
				}
			}
			
			return null;
			
		}
	}
	
	@Override
	public void pokreniFiguru(Object lockObj, AtomicBoolean pauzirano)
	{
		if(this.getIndexPredjenihPolja().size()==0)
		{
			mat.postaviFiguruNaPocetak(this);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Integer> poljaZaPrelazak = this.getListaPoljaZaPrelazak();
		
		if(poljaZaPrelazak!=null)
		{
		for (int i = 0; i < poljaZaPrelazak.size()-1; i++) {
			
			synchronized(lockObj)
			{
				if(pauzirano.get() == true)
				{
					try {
						lockObj.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			mat.ukloniFiguru(this, poljaZaPrelazak.get(i));
			mat.postaviFiguru(this, poljaZaPrelazak.get(i+1));
		
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(this.getIndexPosljednjegPolja()==mat.getPutanja().get(mat.getPutanja().size()-1).getIndexPutanje())
			{
				//System.out.println("To je: " + mat.getPutanja().get(mat.getPutanja().size()-1).getIndexPutanje());
				mat.ukloniFiguru(this, this.getIndexPosljednjegPolja());
				this.setZavrsenPut(true);
				break;
			}
		}
		}
		else
		{
			
		}
	}

}
