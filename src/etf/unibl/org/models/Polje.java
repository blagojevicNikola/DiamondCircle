package etf.unibl.org.models;

import java.io.FileNotFoundException;

import etf.unibl.org.models.interfaces.Figura;
import etf.unibl.org.models.interfaces.LebdecaFiguraInterface;
import etf.unibl.org.views.FiguraWidget;
import etf.unibl.org.views.PoljeWidget;
import javafx.application.Platform;

public class Polje {
	
	private Integer pozicijaX;
	private Integer pozicijaY;
	private Integer vrijednost;
	private Boolean imaDijamant = false;
	private PoljeWidget widget;
	private Figura sadrzanaFigura;
	private Integer indexPutanje;
	private Object mutex = new Object();
	
	public Polje(Integer vr, Integer x, Integer y)
	{
		this.pozicijaX = x;
		this.pozicijaY = y;
		this.vrijednost = vr;
		this.indexPutanje = null;
	}
	
	public void setFigura(Figura f, Integer i)
	{
		PoljeWidget temp = this.widget;
		synchronized(mutex)
		{
			f.dodajPredjenoPolje(indexPutanje);
			sadrzanaFigura = f;	
			if(this.imaDijamant)
			{
				this.sadrzanaFigura.pokupiDijamant();
				this.imaDijamant=false;
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						temp.ukloniDijamant();
					}});
				
			}
			//FiguraWidget fig =new FiguraWidget(f.getBojaFigure(), f.getOznaka());
			widget.postaviFiguraWidget(f.getBojaFigure(), f.getOznaka());
		}
	}
	
	public void removeFigura()
	{
		sadrzanaFigura = null;
		widget.ukloniFiguraWidget();
	}
	
	public void setCrnaRupa()
	{
		if(sadrzanaFigura instanceof LebdecaFiguraInterface)
		{
			widget.postaviCrnuRupu(true);
		}
		else
		{
			sadrzanaFigura = null;
			widget.postaviCrnuRupu(false);
		}
	}
	
	public void removeCrnaRupa()
	{
		widget.ukloniCrnuRupu();
	}
	
	public void setDijamant()
	{
		synchronized(mutex)
		{
			if(this.getFigura()==null)
			{
				this.imaDijamant = true;
				try {
					this.widget.postaviDijamant();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void removeDijamant()
	{
		synchronized(mutex)
		{
			this.imaDijamant = false;
		}
	}
	
	public void removeDijamantWidget()
	{
		this.widget.ukloniDijamant();
	}
	
	public Figura getFigura()
	{
		return sadrzanaFigura;
	}
	
	public Integer getVrijednostPolja()
	{
		return vrijednost;
	}
	
	public void setPoljeWidget(PoljeWidget p)
	{
		widget = p;
	}
	
	public void setIndexPutanje(Integer i)
	{
		this.indexPutanje = i;
	}
	
	public Integer getIndexPutanje()
	{
		return this.indexPutanje;
	}

	public Boolean getImaDijamant() {
		return imaDijamant;
	}

	public void setImaDijamant(Boolean imaDijamant) {
		this.imaDijamant = imaDijamant;
	}
}
