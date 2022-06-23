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

import etf.unibl.org.config.Config;
import etf.unibl.org.enums.Boje;
import etf.unibl.org.models.Igrac;
import etf.unibl.org.models.Matrica;
import etf.unibl.org.models.Polje;
import etf.unibl.org.models.interfaces.Figura;
import etf.unibl.org.views.FiguraLegendaWidget;
import etf.unibl.org.views.KartaWidget;
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

	private Matrica matrica;
	private GridPane matricaWidget;
	private Integer dimenzijaMatrice;
	private Integer brIgraca;
	static private List<Igrac> listaIgraca = new ArrayList<Igrac>();
	private Boolean nemaAktivnihIgara = true;
	private Boolean simulacijaTraje;
	UpravljanjeIgracimaController upravljac;
	
	public void kreirajMatricu(Integer dim, Integer brIg)
	{
		try {
			matrica = new Matrica(dim);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dimenzijaMatrice = dim;
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
		for(int i = 0; i < brIg; i++)
		{	
			Igrac igrac = new Igrac("Igrac" + String.valueOf(i+1), bojeIgraca.get(i), matrica, i);
			for(int j = 0; j < igrac.getListaFiguraIgraca().size(); j++)
			{
				this.figureLegenda.getChildren().add(new FiguraLegendaWidget(igrac.getListaFiguraIgraca().get(j), i+1, matrica.getDimenzija()));
			}
			listaIgraca.add(igrac);
			Label l = new Label("Igrac "+ String.valueOf(i+1));
			l.setStyle("-fx-font-weight: bold; -fx-font-size: 15pt; ");
			l.setTextFill(igrac.getBojaIgraca());
			poljeIgraca.getChildren().add(l);
		}
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("resources/config/config.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			prop.load(inputStream);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File igre = new File(prop.getProperty("rezultati"));
		
		String tempLista[] = igre.list();
		if(tempLista!=null)
		{
			List<String> listaIgara = Arrays.asList(tempLista);
			int k = 0;
			for(String igra:listaIgara)
			{
				if(igra.matches(Config.nazivFajlaRegex))
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
//		File in = new File("src/application/images.png");
//		Image sl = new Image(in.toURI().toString());
//		kartaHolder.setImage(sl);
		upravljac = new UpravljanjeIgracimaController(listaIgraca, matrica, kartaHolder, kartaOpis, timer, sadrzajOpisaKarte, nemaAktivnihIgara, pokreniZaustaviButton);
		
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
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
		RezultatFajloviController fajloviController = loader.getController();
		fajloviController.pripremi();
		Stage stage = new Stage();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}
