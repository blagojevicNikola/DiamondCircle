package etf.unibl.org.models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import etf.unibl.org.controllers.UpravljanjeIgracimaController;
import etf.unibl.org.models.interfaces.Figura;
import etf.unibl.org.models.interfaces.LebdecaFiguraInterface;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;


public class Matrica {
	
	private Integer dimenzija;
	static private List<List<Polje> > matrica = new ArrayList<List<Polje>>();
	static public List<Polje> putanja = new ArrayList<Polje>();
	private GridPane matricaWidget;
	
	public Matrica(Integer dim) throws IOException
	{
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		
		inputStream = new FileInputStream("resources/config/config.properties");
		
		
		prop.load(inputStream);
	
		
		File inputFile = new File(prop.getProperty("putanje")+ dim.toString() +  ".txt");
		FileReader input = null;
		Scanner sc = null;

		
		input = new FileReader(inputFile);
		sc = new Scanner(input).useDelimiter(",");
	

		List<Integer> listaPozicija = new ArrayList<Integer>();
		while(sc.hasNext())
		{
			listaPozicija.add(sc.nextInt());
		}
		sc.close();
		
		this.setDimenzija(dim);
		for(int i = 0; i < dim; i++)
		{
			List<Polje> r = new ArrayList<Polje>();
			for(int j = 0; j < dim; j++)
			{
				Polje temp = new Polje((i*dim) + (j+1), i, j);
				r.add(temp);
			}
			matrica.add(r);
		}

		for(int i = 0; i < listaPozicija.size(); i++)
		{
			Polje p = getPoljeZaPutanju(this.matrica, listaPozicija.get(i));
			//System.out.println(p.getVrijednostPolja());
			p.setIndexPutanje(i);
			putanja.add(p) ;
		}
		
		inputStream.close();
	}
	
	public void postaviFiguruNaPocetak(Figura f)
	{
		putanja.get(0).setFigura(f);
	}
	
	public void postaviFiguru(Figura f, Integer i)
	{	
		
		putanja.get(i).setFigura(f);
		
	}
	
	public void ukloniFiguru(Figura f, Integer i)
	{
		if(i!=null)
		{
			putanja.get(i).removeFigura();
		}
		
	}
	
	public void postaviCrneRupe(List<Integer> lista)
	{
		
		for(Integer j : lista)
		{
			Polje temp = putanja.get(j);
			
			if(temp.getFigura()!=null)
			{
				Boolean figuraLebdi = temp.getFigura() instanceof LebdecaFiguraInterface;
				if(figuraLebdi==false)
				{
					UpravljanjeIgracimaController.listaZavrsenihFigura.get(temp.getFigura().getIgracVlasnik().getIndexIgraca()).add(temp.getFigura());
					temp.getFigura().oduzmiFiguruOdIgraca();
				}
			}
		}
		
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				for(int i = 0; i < lista.size(); i++)
				{
					putanja.get(lista.get(i)).setCrnaRupa();
				}
			}});
		
	}
	
	public void ukloniCrneRupe(List<Integer> lista)
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				for(int i = 0; i < lista.size(); i++)
				{
					putanja.get(lista.get(i)).removeCrnaRupa();
				}
			}});
		//putanja.get(i).removeCrnaRupa();
	}
	
	public void postaviDijamante(List<Integer> lista)
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				for(int i = 0; i < lista.size();i++)
				{
					putanja.get(lista.get(i)).setDijamant();
				}
			}
			
		});
		
	}
	
	public void ukloniDijamante(List<Integer> lista)
	{
		for(int i = 0; i < lista.size(); i++)
		{
			putanja.get(lista.get(i)).removeDijamant();
		}
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
			for(int i = 0; i < lista.size(); i++)
			{
				putanja.get(lista.get(i)).removeDijamantWidget();
			}
			
		}
		}
		);
	}
	
	public Polje getPolje(Integer x, Integer y)
	{
		return matrica.get(x).get(y);
	}
	
	public GridPane getMatricaWidget()
	{
		return matricaWidget;
	}
	
	public void setMatricaWidget(GridPane g)
	{
		matricaWidget = g;
	}
	
	public Integer getDuzinaPutanje()
	{
		return putanja.size();
	}
	
	public List<Polje> getPutanja()
	{
		return this.putanja;
	}
	
//	private void spiralDiamondView(List<List<Polje>> matrix, 
//	        int x, int y, int m, int n, int k, List<Polje> nova)
//	    {
//	        // Get middle column
//	        int midCol = y + ((n - y) / 2);
//	        int midRow = midCol;
//	        
//	        for (int i = midCol, j = 0; 
//	             i < n && k > 0; ++i, k--, j++)
//	        {
//	            nova.add(matrix.get(x + j).get(i));
//	        }
//	        
//	        for (int i = n, j = 0; 
//	             i >= midCol && k > 0; --i, k--, j++)
//	        {
//	            nova.add(matrix.get(midRow+j).get(i));
//	        }
//	        
//	        for (int i = midCol - 1, j = 1; 
//	             i >= y && k > 0; --i, k--, j++)
//	        {
//	            nova.add(matrix.get((n)-j).get(i));
//	        }
//	       
//	        for (int i = y + 1, j = 1; 
//	             i < midCol && k > 0; ++i, k--, j++)
//	        {
//	            nova.add(matrix.get((midRow) - j).get(i));
//	        }
//	        if (x + 1 <= m - 1 && k > 0)
//	        {
//	            // Recursive call
//	            spiralDiamondView(matrix, x + 1, 
//	                              y + 1, m - 1, n - 1, k, nova);
//	        }
//	    }
//	    private List<Polje> diamondSpiral(List<List<Polje>> matrix, int size)
//	    {
//	        // Get the size
//	        int row = size;
//	        int col = size;
//	        List<Polje> nova = new ArrayList<Polje>();
//	        
////	        if (row != col || col % 2 == 0)
////	        {
////	            System.out.println("\nNot perfect odd square matrix");
////	            return;
////	        }
//	        // Print result
//	        spiralDiamondView(matrix, 0, 0, row - 1, 
//	            col - 1, (row * col) - ((col + 1) / 2) * 4, nova);
//	        
//	        return nova;
//	    }
	    
	    private Polje getPoljeZaPutanju(List<List<Polje>> mat, Integer vrijednost)
	    {
	    	Polje p = mat
	    	  .stream()
	    	  .flatMap(Collection::stream).filter(d -> vrijednost.equals(d.getVrijednostPolja())).findFirst().orElse(null);
	    	return p;
	    	  
	    }

		public Integer getDimenzija() {
			return dimenzija;
		}

		public void setDimenzija(Integer dimenzija) {
			this.dimenzija = dimenzija;
		}
}
