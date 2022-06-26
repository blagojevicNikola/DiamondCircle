package etf.unibl.org.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Main;
import etf.unibl.org.file_writer.RezultatFile;
import etf.unibl.org.models.Duh;
import etf.unibl.org.models.Igrac;
import etf.unibl.org.models.Matrica;
import etf.unibl.org.models.ObicnaKarta;
import etf.unibl.org.models.SpecijalnaKarta;
import etf.unibl.org.models.SpilKarata;
import etf.unibl.org.models.TimerIgre;
import etf.unibl.org.models.interfaces.Figura;
import etf.unibl.org.models.interfaces.Karta;
import etf.unibl.org.models.interfaces.SpecijalnaKartaInterface;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpravljanjeIgracimaController implements Runnable{
	
	private List<Igrac> listaIgraca = new ArrayList<Igrac>();
	private SpilKarata spil = new SpilKarata();
	private Matrica matrica;
	private ImageView kartaHolder;
	private Label kartaOpis;
	private Label timer;
	private Boolean igraJeZavrsena;
	private Label sadrzajOpisaKarte;
	public static List<List<Figura>> listaZavrsenihFigura = new ArrayList<List<Figura>>();
	private AtomicBoolean pauzirano = new AtomicBoolean(false);
	private Object lockObj = new Object();
	private Boolean nemaAktivnihIgara;
	private Button pokreniZaustaviButton;

	public static FileHandler handler;
	
	static{
		try
		{
			handler = new FileHandler("UpravljanjeIgracimaController.log");
			Logger.getLogger(UpravljanjeIgracimaController.class.getName()).addHandler(handler);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public UpravljanjeIgracimaController(List<Igrac> lista, Matrica mat, ImageView kartaHolder, Label kartaOpis, Label timer, Label sadrzajOpisaKarte, Boolean nemaAktivnihIgara, Button pokreniZaustaviButton)
	{
		this.listaIgraca = lista;
		this.matrica = mat;
		this.kartaHolder = kartaHolder;
		this.kartaOpis = kartaOpis;
		this.igraJeZavrsena = false;
		this.timer = timer;
		this.sadrzajOpisaKarte = sadrzajOpisaKarte;
		this.pokreniZaustaviButton = pokreniZaustaviButton;
		for(int i = 0; i < lista.size(); i++)
		{
			listaZavrsenihFigura.add(new ArrayList<Figura>());
		}
	}
	
	@Override
	public void run() {
		
		Duh duh = new Duh(this.matrica, this.pauzirano, this.lockObj);
		TimerIgre timerIgre = new TimerIgre(timer, this.pauzirano, this.lockObj);
		Thread timerThread = new Thread(timerIgre);
		Thread duhThread = new Thread(duh);
		timerThread.setDaemon(true);
		duhThread.setDaemon(true);
		timerThread.start();
		duhThread.start();
		while(this.igraJeZavrsena==false)
		{
		for(int i = 0; i<listaIgraca.size(); i++) {
			
			synchronized(lockObj)
			{
			if(pauzirano.get() == true)
			{
				try {
					lockObj.wait();
				} catch (InterruptedException e) {
					Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
			}
			}
			
			Karta izvucenaKarta = spil.izvuciKartu();
			
			if(izvucenaKarta instanceof SpecijalnaKartaInterface)
			{
				SpecijalnaKarta izvucenaSpecKarta = (SpecijalnaKarta) izvucenaKarta;
				
				Properties prop = new Properties();
				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream("resources/config/config.properties");
				} catch (FileNotFoundException e) {
					Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
				try {
					prop.load(inputStream);
				} catch (IOException e) {
					Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
				
				File in = new File(prop.getProperty("slikaKarteCrnaRupa"));
				Image sl = new Image(in.toURI().toString());
					
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						kartaHolder.setImage(sl);
						kartaOpis.setText(izvucenaSpecKarta.getBrojCrnihRupa().toString());
						postaviOpisCrneKarte(izvucenaSpecKarta.getOpisKarte(matrica));
					}});
				
				izvucenaSpecKarta.postaviCrneRupe(matrica);
				
				
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						kartaHolder.setImage(null);
						kartaOpis.setText("");
						sadrzajOpisaKarte.setText("");
					}});
				
				try {
					inputStream.close();
				} catch (IOException e) {
					Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
				
			}
			else
			{
				
				Figura pokrenutaFigura = listaIgraca.get(i).getFiguraIgraca();
				
				if(pokrenutaFigura!=null)
				{
					ObicnaKarta izvucenaObicnaKarta = (ObicnaKarta) izvucenaKarta;
					
					Properties prop = new Properties();
					InputStream inputStream = null;
					try {
						inputStream = new FileInputStream("resources/config/config.properties");
					} catch (FileNotFoundException e) {
						Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
					try {
						prop.load(inputStream);
					} catch (IOException e) {
						Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
					
					File in = new File(prop.getProperty("slikaObicneKarte"));
					Image sl = new Image(in.toURI().toString());
					pokrenutaFigura.setBrZadanihPolja(izvucenaObicnaKarta.getBroj());
					pokrenutaFigura.getListaPoljaZaPrelazak();
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							kartaHolder.setImage(sl);;
							kartaOpis.setText(String.valueOf(izvucenaObicnaKarta.getBroj()));
							postaviOpisObicneKarte(pokrenutaFigura);
						}});
					
					try {
						Thread.sleep(500);
					} catch (InterruptedException e1) {
						Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
					}
					
					pokrenutaFigura.pokreniFiguru(this.lockObj, this.pauzirano);
					
					
					//pokrenutaFigura.setIndexCilja(izvucenaObicnaKarta.getBroj());
//					Thread t = new Thread(pokrenutaFigura);
//					t.setDaemon(true);
//					t.start();
//					try {
//						t.join();
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					if(pokrenutaFigura.getZavrsenPut())
					{
						listaZavrsenihFigura.get(pokrenutaFigura.getIgracVlasnik().getIndexIgraca()).add(pokrenutaFigura);
						listaIgraca.get(i).removeFiguraIgraca(pokrenutaFigura);
					}
					Platform.runLater(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							kartaHolder.setImage(null);
							kartaOpis.setText("");
							sadrzajOpisaKarte.setText("");
						}});
					
					try {
						inputStream.close();
					} catch (IOException e) {
						Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
					
				}
				else
				{
					listaIgraca.remove(i);
					if(listaIgraca.isEmpty())
					{
						this.igraJeZavrsena=true;
					}
				}
			}		
		}
	}
		duh.stopDuh();
		timerIgre.stopTimer();
		this.nemaAktivnihIgara = true;
		this.resetPokreniZaustaviButton();
		RezultatFile upisivanjeRezultata = new RezultatFile(this.matrica);
		try {
			upisivanjeRezultata.upisiRezultatIgre(timer.getText());
		} catch (IOException e) {
			Logger.getLogger(UpravljanjeIgracimaController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
		handler.close();
		
	}
	
	private void postaviOpisCrneKarte(String pozicije)
	{
		this.sadrzajOpisaKarte.setText("Izvucena je crna karta, i postavljaju se rupe na pozicije: "+pozicije);
	}
	
	private void postaviOpisObicneKarte(Figura f)
	{
		this.sadrzajOpisaKarte.setText("Na potezu je " + f.getIgracVlasnik().getNaziv() + "-Figura " + (f.getIndexFigure()+1) + ", figura prelazi sa polja "
				+ matrica.getPutanja().get(f.getIndexPosljednjegPolja()).getVrijednostPolja() + " na polje " + matrica.getPutanja().get(f.getIndexCilja()).getVrijednostPolja());
	}
	
	public void pauzirajSimulaciju()
	{
		this.pauzirano.set(true);
	}
	
	public void nastaviSimulaciju()
	{
		synchronized(lockObj)
		{
			this.pauzirano.set(false);
			this.lockObj.notifyAll();
		}
	}
	
	public void resetPokreniZaustaviButton()
	{
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				pokreniZaustaviButton.setText("Start");
			}
			
		});
		
	}
	
}
