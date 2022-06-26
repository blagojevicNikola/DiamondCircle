package etf.unibl.org.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import etf.unibl.org.file_writer.RezultatFile;

public class Duh implements Runnable {
	
	private Matrica matrica;
	private Integer brojDijamanata;
	private volatile boolean exit = false;
	private Object lockObj;
	private AtomicBoolean pauzirano;
	
	public static FileHandler handler;
	
	static{
		try
		{
			handler = new FileHandler("Duh.log");
			Logger.getLogger(Duh.class.getName()).addHandler(handler);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public Duh(Matrica matrica, AtomicBoolean pauzirano, Object lockObj) {
		this.matrica = matrica;
		//this.brojDijamanata = 4;
		this.pauzirano = pauzirano;
		this.lockObj = lockObj;
	}
	
	@Override
	public void run() {
		
		while(!exit)
		{
			
			List<Integer> listaPozicijaDijamanata = izaberiPozicijeDijamanata();
			matrica.postaviDijamante(listaPozicijaDijamanata);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Logger.getLogger(Duh.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
			
			synchronized(lockObj)
			{
				if(pauzirano.get()==true)
				{
					try {
						lockObj.wait();
					} catch (InterruptedException e) {
						Logger.getLogger(Duh.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
				}
			}
			
			matrica.ukloniDijamante(listaPozicijaDijamanata);
		}
		
		handler.close();
	}
	
	private List<Integer> izaberiPozicijeDijamanata()
	{
		int i = 0;
		Random rand = new Random();
		List<Integer> lista = new ArrayList<Integer>();
		this.brojDijamanata = rand.nextInt(matrica.getDimenzija()-2)+2;
		while(i<brojDijamanata)
		{
			Integer k = rand.nextInt(matrica.getDuzinaPutanje());
			if(!lista.contains(k) && !matrica.getPutanja().get(k).getImaDijamant())
			{
				lista.add(k);
				i++;
			}
		}
		return lista;
	}
	
	public void stopDuh()
	{
		exit = true;
	}
	

}
