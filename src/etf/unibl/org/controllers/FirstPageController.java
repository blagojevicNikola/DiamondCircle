package etf.unibl.org.controllers;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import etf.unibl.org.config.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FirstPageController {

	@FXML private TextField dimenzijeMatriceTextField;
	@FXML private TextField brojIgracaTextField;
	@FXML private Button potvrdiButton;
	@FXML private Button izadjiButton;
	
	
	public void potvrdiUnos() throws Exception
	{
		Properties prop = new Properties();
		InputStream inputStream = new FileInputStream("resources/config/config.properties");
		prop.load(inputStream);
		
		String dimenzija = dimenzijeMatriceTextField.getText();
		String brIgraca = brojIgracaTextField.getText();
		if(dimenzija.matches(prop.getProperty("dimenzijaMatriceRegex")) && brIgraca.matches(prop.getProperty("brojIgracaRegex")))
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/etf/unibl/org/views/MainPageView.fxml"));
			BorderPane root = (BorderPane) loader.load();
			
			MainPageController controller = loader.getController();
			controller.kreirajMatricu(Integer.parseInt(dimenzija), Integer.parseInt(brIgraca));
			Stage stage = (Stage) potvrdiButton.getScene().getWindow();
			stage.sizeToScene();
			stage.setResizable(false);
			stage.setScene(new Scene(root));
			stage.setMinHeight(stage.getHeight());
			stage.setMinWidth(stage.getWidth());
			stage.centerOnScreen();
			
		}
		else
		{
			Alert a = new Alert(AlertType.ERROR);
			a.show();
		}
		inputStream.close();

	}
	
	public void zatvoriAplikaciju(ActionEvent event)
	{
		Stage stage = (Stage) izadjiButton.getScene().getWindow();
		stage.close();
	}
}
