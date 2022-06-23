package etf.unibl.org.views;


import java.io.IOException;

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
	
	public FiguraLegendaWidget(Figura figura, Integer indexIgraca, Integer dimenzija)
	{
		super("Figura " + figura.getOznaka() + " (" + indexIgraca + ")");
		
		super.setMinWidth(120);
		this.setBoja(figura);
		
		this.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/etf/unibl/org/views/PredjenPutFormView.fxml"));
				AnchorPane root = null;
				try {
					root = (AnchorPane) loader.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
				PredjenaPutanjaFormController formaController = loader.getController();
				formaController.ucitajPutanjuFigure(figura, dimenzija);
				Stage stage = new Stage();
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
		    }
		});
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
