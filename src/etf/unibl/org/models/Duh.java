package etf.unibl.org.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Duh implements Runnable {
	
	private Matrica matrica;
	private Integer brojDijamanata;
	private volatile boolean exit = false;
	private Object lockObj;
	private AtomicBoolean pauzirano;
	
	public Duh(Matrica matrica, AtomicBoolean pauzirano, Object lockObj) {
		this.matrica = matrica;
		//this.brojDijamanata = 4;
		this.pauzirano = pauzirano;
		this.lockObj = lockObj;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!exit)
		{
			synchronized(lockObj)
			{
				if(pauzirano.get()==true)
				{
					try {
						lockObj.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			List<Integer> listaPozicijaDijamanata = izaberiPozicijeDijamanata();
			matrica.postaviDijamante(listaPozicijaDijamanata);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			matrica.ukloniDijamante(listaPozicijaDijamanata);
		}

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
			if(!lista.contains(k))
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
