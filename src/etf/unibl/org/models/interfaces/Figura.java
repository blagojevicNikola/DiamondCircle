package etf.unibl.org.models.interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import etf.unibl.org.enums.Boje;
import etf.unibl.org.models.Igrac;
import etf.unibl.org.models.Matrica;
import etf.unibl.org.views.FiguraWidget;
import javafx.scene.paint.Color;

public abstract class Figura{
	
	private Integer pozicijaX;
	private Integer pozicijaY;
	private Boje enumBoja;
	private Integer indexFigure;
	private Color bojaFigure;
	private String oznaka;
	protected Integer indexCilja;
	//private Integer brPredjenihPolja;
	protected Integer brojPokupljenihDijamanata;
	protected Integer brZadanihPolja;
	private Boolean zavrsenPut;
	protected FiguraWidget widget;
	protected Matrica mat;
	private Igrac igracVlasnik;
	protected List<Integer> indexPredjenihPolja = new ArrayList<Integer>();
	//protected List<Integer> listaPoljaZaPrelazak = new ArrayList<Integer>();
	
	public Figura(Matrica m, String oznaka, Color boja, Igrac igrac, Integer indexFigure)
	{
		this.mat = m;
		this.oznaka = oznaka;
		this.brZadanihPolja = 0;
		this.zavrsenPut = false;
		this.igracVlasnik = igrac;
		this.brojPokupljenihDijamanata = 0;
		this.bojaFigure = boja;
		this.indexFigure = indexFigure;
		//this.brPredjenihPolja = null;
	}
	
	public String getOznaka()
	{
		return oznaka;
	}
	
	public Color getBojaFigure()
	{
		return bojaFigure;
	}
	
	public void setFiguraWidget(FiguraWidget f)
	{
		widget = f;
	}

//	public Integer getBrPredjenihPolja() {
//		return brPredjenihPolja;
//	}

//	public void setBrPredjenihPolja(Integer brPredjenihPolja) {
//		this.brPredjenihPolja = brPredjenihPolja;
//	}
	
	abstract public void getListaPoljaZaPrelazak();
	abstract public void pokreniFiguru(Object lockObj, AtomicBoolean pauzirano);
	
	protected List<Integer> popuniListu(Integer start, Integer end)
	{
		if(start!=end)
		{
			List<Integer> lista = new ArrayList<Integer>();
			lista.add(start);
			for(int i = start+1; i < end; i++)
			{
				if(mat.getPutanja().get(i).getFigura()==null)
				{
					lista.add(i);
				}
			}
			lista.add(end);
			return lista;
		}
		else
		{
			return null;
		}
	}
	
	public void setBrZadanihPolja(Integer i)
	{
		this.brZadanihPolja = i+ this.brojPokupljenihDijamanata;
		this.brojPokupljenihDijamanata = 0;
	}
	
	public Integer getBrZadanihPolja()
	{
		return this.brZadanihPolja;
	}
	
	public void dodajPredjenoPolje(Integer i)
	{
		indexPredjenihPolja.add(i);
	}
	
	public List<Integer> getIndexPredjenihPolja()
	{
		return indexPredjenihPolja;
	}
	
	public Integer getIndexPosljednjegPolja()
	{
		if(indexPredjenihPolja.size()==0)
		{
			return 0;
		}
		else
		{
			return indexPredjenihPolja.get(indexPredjenihPolja.size()-1);
		}
		
	}

	public Integer getIndexCilja() {
		return indexCilja;
	}

	public void setIndexCilja(Integer indexCilja) {
		
	}

	public Boolean getZavrsenPut() {
		return zavrsenPut;
	}

	public void setZavrsenPut(Boolean zavrsenPut) {
		this.zavrsenPut = zavrsenPut;
	}

	public Igrac getIgracVlasnik() {
		return igracVlasnik;
	}
	
	public void oduzmiFiguruOdIgraca()
	{
		igracVlasnik.removeFiguraIgraca(this);
	}
	
	public void pokupiDijamant()
	{
		this.brojPokupljenihDijamanata++;
	}

	public Integer getIndexFigure() {
		return indexFigure;
	}

	public void setIndexFigure(Integer indexFigure) {
		this.indexFigure = indexFigure;
	}
	
}
