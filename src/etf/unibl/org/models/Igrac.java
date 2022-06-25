package etf.unibl.org.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import etf.unibl.org.enums.Boje;
import etf.unibl.org.enums.TipFigure;
import etf.unibl.org.models.interfaces.Figura;
import javafx.scene.paint.Color;

public class Igrac {
	
	private String naziv;
	private Boje bojaIgraca;
	private Integer indexIgraca;
	private List<Figura> listaFigura = new ArrayList<Figura>();
	private Color bojaPaint;
	
	public Igrac(String naziv, Boje bojaIgraca, Matrica m, Integer index)
	{
		this.setNaziv(naziv);
		this.bojaIgraca = bojaIgraca;
		this.indexIgraca = index;
		switch(bojaIgraca)
		{
		case CRVENA:
			this.bojaPaint = Color.RED;
			break;
		case PLAVA:
			this.bojaPaint = Color.DEEPSKYBLUE;
			break;
		case ZELENA:
			this.bojaPaint = Color.GREEN;
			break;
		case ZUTA:
			this.bojaPaint = Color.YELLOW;
			break;
		default:
			break;
		
		}
		
		for(int i = 0; i < 4; i++)
		{
			TipFigure t = TipFigure.getRandomTipFigure();
			switch(t)
			{
			case LEBDECA:
				listaFigura.add(new LebdecaFigura(m,"L", this.bojaPaint, this, i));
				break;
			case OBICNA:
				listaFigura.add(new ObicnaFigura(m,"O", this.bojaPaint, this, i));
				break;
			case SUPERBRZA:
				listaFigura.add(new SuperBrzaFigura(m,"S", this.bojaPaint, this, i));
				break;
			default:
				break;
			
			}
		
		}
	}
	
	public Figura getFiguraIgraca()
	{
		if(this.listaFigura.isEmpty())
		{
			return null;
		}
		else
		{
			return this.listaFigura.get(0);
		}
		
	}
	
	public void removeFiguraIgraca(Figura f)
	{
		this.listaFigura.remove(f);
	}
	
	public Color getBojaIgraca()
	{
		return this.bojaPaint;
	}
	
	public List<Figura> getListaFiguraIgraca()
	{
		return this.listaFigura;
	}
	
	public Integer getIndexIgraca()
	{
		return this.indexIgraca;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
}
