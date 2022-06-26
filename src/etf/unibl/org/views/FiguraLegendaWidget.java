package etf.unibl.org.views;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import etf.unibl.org.config.Config;
import etf.unibl.org.controllers.PredjenaPutanjaFormController;
import etf.unibl.org.models.interfaces.Figura;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FiguraLegendaWidget extends Button {

	//Figura figura;
	public static FileHandler handler;
	
	static{
		try {
			Properties prop = new Properties();
			InputStream inputStream = new FileInputStream("resources/config/config.properties");
			prop.load(inputStream);
			handler = new FileHandler(prop.getProperty("logFolder") + "FiguraLegendaWidget.log");
			Logger.getLogger(FiguraLegendaWidget.class.getName()).addHandler(handler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public FiguraLegendaWidget(Figura figura, Integer indexIgraca, Integer dimenzija)
	{
		super("Figura " + figura.getOznaka() + " (" + indexIgraca + ")");
		
		super.setMinWidth(Config.MIN_SIRINA_FIGURA_BUTTON);
		this.setBoja(figura);
		
		this.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/etf/unibl/org/views/PredjenPutFormView.fxml"));
				AnchorPane root = null;
				try { 
					root = (AnchorPane) loader.load(); 
				} catch (IOException e1) {
					Logger.getLogger(FiguraLegendaWidget.class.getName()).log(Level.SEVERE, e1.fillInStackTrace().toString());
					//handler.close();
				}
		    	
				PredjenaPutanjaFormController formaController = loader.getController();
				try {
					formaController.ucitajPutanjuFigure(figura, dimenzija);
				} catch (IOException e1) {
					Logger.getLogger(FiguraLegendaWidget.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
					//handler.close();
				}
				
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
		    }
		});
		
		handler.close();
	}
	
	private void setBoja(Figura f)
	{
		if(f.getBojaFigure()==Color.RED)
		{
			super.setStyle("-fx-background-color: #FF0000;"
					+ "-fx-font-weight:bold;");
		}
		else if(f.getBojaFigure() == Color.DEEPSKYBLUE)
		{
			super.setStyle("-fx-background-color: #00BFFF;"
					+ "-fx-font-weight:bold;");
		}
		else if(f.getBojaFigure() == Color.GREEN)
		{
			super.setStyle("-fx-background-color: #008000;"
					+ "-fx-font-weight:bold;");
		}
		else
		{
			super.setStyle("-fx-background-color: #FFFF00;" 
					+ "-fx-font-weight:bold;");
		}
	}
	
}
