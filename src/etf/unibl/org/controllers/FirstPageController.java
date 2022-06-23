package etf.unibl.org.controllers;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	
	
	public static Handler handler;
	
	{
		try {
			handler = new FileHandler("FirstPageController.log");
			Logger.getLogger(FirstPageController.class.getName()).addHandler(handler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void potvrdiUnos() 
	{
		Properties prop = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("resources/config/config.properties");
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, e.fillInStackTrace().toString());
		} catch (IOException e)
		{
			Logger.getLogger(FirstPageController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		
		
		String dimenzija = dimenzijeMatriceTextField.getText();
		String brIgraca = brojIgracaTextField.getText();
		if(dimenzija.matches(prop.getProperty("dimenzijaMatriceRegex")) && brIgraca.matches(prop.getProperty("brojIgracaRegex")))
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/etf/unibl/org/views/MainPageView.fxml"));
			BorderPane root = null;
			
			try {
				root = (BorderPane) loader.load();
			} catch (IOException e) {
				
			}
			
			MainPageController controller = loader.getController();
			
			try {
				controller.kreirajMatricu(Integer.parseInt(dimenzija), Integer.parseInt(brIgraca));
			} catch (NumberFormatException | IOException e) {
				Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, e.fillInStackTrace().toString());
			}
			
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
		
		try {
			inputStream.close();
		} catch (IOException e) {
			Logger.getLogger(FirstPageController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}

	}
	
	public void zatvoriAplikaciju(ActionEvent event)
	{
		Stage stage = (Stage) izadjiButton.getScene().getWindow();
		stage.close();
	}
}
