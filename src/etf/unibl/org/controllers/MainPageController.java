package etf.unibl.org.controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import etf.unibl.org.config.Config;
import etf.unibl.org.enums.Boje;
import etf.unibl.org.models.Igrac;
import etf.unibl.org.models.Matrica;
import etf.unibl.org.models.Polje;
import etf.unibl.org.views.FiguraLegendaWidget;

import etf.unibl.org.views.PoljeWidget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainPageController {
	
	@FXML private Label brojOdigranihIgara;
	@FXML private StackPane gridHolder;
	@FXML private Button pokreniZaustaviButton;
	@FXML private HBox poljeIgraca;
	@FXML private ImageView kartaHolder;
	@FXML private Label kartaOpis;
	@FXML private Label timer;
	@FXML private VBox figureLegenda;
	@FXML private Button listaFajlovaButton;
	@FXML private Label sadrzajOpisaKarte;
	
	public static FileHandler handler;
	
	{
		try {
			handler = new FileHandler("MainPageController.log");
			Logger.getLogger(MainPageController.class.getName()).addHandler(handler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Matrica matrica;
	private GridPane matricaWidget;
	private Integer dimenzijaMatrice;
	private Integer brIgraca;
	static private List<Igrac> listaIgraca = new ArrayList<Igrac>();
	private Boolean nemaAktivnihIgara = true;
	private Boolean simulacijaTraje;
	UpravljanjeIgracimaController upravljac;
	
	public void kreirajMatricu(Integer dim, List<String> igraci) throws IOException, FileNotFoundException
	{
		
		
		matrica = new Matrica(dim);
		this.setDimenzijaMatrice(dim);
		this.setBrIgraca(igraci.size());
		this.matricaWidget = new GridPane();
		matricaWidget.setAlignment(Pos.CENTER);
		
		matricaWidget.setVgap(2); 
	    matricaWidget.setHgap(2);   
		for(int i = 0; i < dim; i++)
		{
			for(int j= 0; j < dim; j++)
			{
			 Polje tempPolje = matrica.getPolje(i, j);
			 PoljeWidget tempPoljeWidget = new PoljeWidget(String.valueOf(tempPolje.getVrijednostPolja()));
			 matricaWidget.add(tempPoljeWidget, j, i);
			 tempPolje.setPoljeWidget(tempPoljeWidget);
			}
		}
		gridHolder.getChildren().addAll(matricaWidget);
		matrica.setMatricaWidget(matricaWidget);
		List<Boje> bojeIgraca = new ArrayList<Boje>(Arrays.asList(Boje.values()));
		Collections.shuffle(bojeIgraca);
		for(int i = 0; i < this.brIgraca; i++)
		{	
			Igrac igrac = new Igrac(igraci.get(i), bojeIgraca.get(i), matrica, i);
			listaIgraca.add(igrac);
		}
		Collections.shuffle(listaIgraca);
		
		for(int i = 0; i < listaIgraca.size(); i++)
		{
			for(int j = 0; j < listaIgraca.get(i).getListaFiguraIgraca().size(); j++)
			{
				this.figureLegenda.getChildren().add(new FiguraLegendaWidget(listaIgraca.get(i).getListaFiguraIgraca().get(j), listaIgraca.get(i).getIndexIgraca()+1, matrica.getDimenzija()));
			}
			
			Label l = new Label(listaIgraca.get(i).getNaziv());
			l.setStyle("-fx-font-weight: bold; -fx-font-size: 15pt; ");
			l.setTextFill(listaIgraca.get(i).getBojaIgraca());
			poljeIgraca.getChildren().add(l);
		}
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("resources/config/config.properties");
			prop.load(inputStream);
		} catch (FileNotFoundException e1) {
			throw e1;
		}
		 catch (IOException e1) {
			throw e1;
		}
		
		File igre = new File(prop.getProperty("rezultati"));
		
		String tempLista[] = igre.list();
		if(tempLista!=null)
		{
			List<String> listaIgara = Arrays.asList(tempLista);
			int k = 0;
			for(String igra:listaIgara)
			{
				if(igra.matches(prop.getProperty("nazivFajlaRegex")))
				{
					k++;
				}
			}
			brojOdigranihIgara.setText(String.valueOf(k));
		}
		else
		{
			brojOdigranihIgara.setText("0");
		}
		
		try {
			inputStream.close();
		} catch (IOException e) {
			 throw e;
		}
	
//		File in = new File("src/application/images.png");
//		Image sl = new Image(in.toURI().toString());
//		kartaHolder.setImage(sl);
		upravljac = new UpravljanjeIgracimaController(listaIgraca, matrica, kartaHolder, kartaOpis, timer, sadrzajOpisaKarte, nemaAktivnihIgara, pokreniZaustaviButton);
		handler.close();
	}
	
	public void izmjeniStanjeIgre()
	{
		if(pokreniZaustaviButton.getText().equals("Start"))
		{
			pokreniZaustaviButton.setText("Stop");
		}
		else
		{
			pokreniZaustaviButton.setText("Start");
		}
		
	}
	
	public void pokreniZaustavi()
	{
		if(this.nemaAktivnihIgara)
		{
			this.nemaAktivnihIgara = false;
			this.simulacijaTraje = true;
			//UpravljanjeIgracimaController upravljac = new UpravljanjeIgracimaController(listaIgraca, matrica, kartaHolder, kartaOpis, timer, sadrzajOpisaKarte);
			Thread upravljacThread = new Thread(upravljac);
			upravljacThread.setDaemon(true);
			upravljacThread.start(); 
			
		}
		else
		{
			if(this.simulacijaTraje)
			{
				this.simulacijaTraje = false;
				upravljac.pauzirajSimulaciju();
			}
			else
			{
				this.simulacijaTraje = true;
				upravljac.nastaviSimulaciju();
			}
		}
	}
	
	public void ucitajListuFajlova()
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/etf/unibl/org/views/RezultatFajloviView.fxml"));
		AnchorPane root = null;
		try {
			root = (AnchorPane) loader.load();
		} catch (IOException e) {
			Logger.getLogger(MainPageController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
    	
		RezultatFajloviController fajloviController = loader.getController();
		
		try {
			fajloviController.pripremi();
		} catch (IOException e) {
			Logger.getLogger(MainPageController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void getBrojFajlova()
	{
		
	}

	public Integer getDimenzijaMatrice() {
		return dimenzijaMatrice;
	}

	public void setDimenzijaMatrice(Integer dimenzijaMatrice) {
		this.dimenzijaMatrice = dimenzijaMatrice;
	}

	public Integer getBrIgraca() {
		return brIgraca;
	}

	public void setBrIgraca(Integer brIgraca) {
		this.brIgraca = brIgraca;
	}
	
	
}
