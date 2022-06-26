package etf.unibl.org.controllers;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import etf.unibl.org.config.Config;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class FirstPageController {

	@FXML private TextField dimenzijeMatriceTextField;
	@FXML private ChoiceBox<String> brojIgracaChoiceBox;
	@FXML private Button potvrdiButton;
	@FXML private Button izadjiButton;
	@FXML private HBox listaIgracaHBox;
	
	List<TextField> igraciTextField = new ArrayList<TextField>();
	
	private ObservableList<String> listaBrojaIgraca = FXCollections.observableArrayList(List.of("2", "3", "4"));
	
	public static FileHandler handler;
	
	{
		try {
			handler = new FileHandler("FirstPageController.log");
			Logger.getLogger(FirstPageController.class.getName()).addHandler(handler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void initialize()
	{
		
		for(int i = 0; i < 4; i++)
		{
			TextField temp = new TextField();
			temp.setPromptText("Igrac " + String.valueOf(i+1));
			temp.setMaxSize(135, 35);
			temp.setMinSize(135, 35);
			temp.setStyle("-fx-font-size: 15;");
			igraciTextField.add(temp);
		}
		brojIgracaChoiceBox.setItems(listaBrojaIgraca);
		brojIgracaChoiceBox.setValue(listaBrojaIgraca.get(0));
		listaIgracaHBox.getChildren().addAll(igraciTextField.subList(0, Integer.valueOf(listaBrojaIgraca.get(0))));
	
		
		brojIgracaChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				listaIgracaHBox.getChildren().clear();
				listaIgracaHBox.getChildren().addAll(igraciTextField.subList(0, Integer.valueOf(brojIgracaChoiceBox.getSelectionModel().getSelectedItem())));
			}});
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
			//handler.close();
		} catch (IOException e)
		{
			Logger.getLogger(FirstPageController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			//handler.close();
		}
		
		
		String dimenzija = dimenzijeMatriceTextField.getText();
		
		
		if(dimenzija.matches(prop.getProperty("dimenzijaMatriceRegex")) && this.ispravniPodaciIgraca())
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/etf/unibl/org/views/MainPageView.fxml"));
			BorderPane root = null;
			
			try {
				root = (BorderPane) loader.load();
			} catch (IOException e) {
				Logger.getLogger(FirstPageController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				//handler.close();
			}
			
			MainPageController controller = loader.getController();
			
			try {
				controller.kreirajMatricu(Integer.parseInt(dimenzija), this.getImenaIgraca());
			} catch (NumberFormatException | IOException e) {
				Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, e.fillInStackTrace().toString());
				//handler.close();
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
			//handler.close();
		}
		handler.close();
	}
	
	public void zatvoriAplikaciju(ActionEvent event)
	{
		Stage stage = (Stage) izadjiButton.getScene().getWindow();
		stage.close();
	}
	
	public Boolean ispravniPodaciIgraca()
	{
		
		List<String> imena = new ArrayList<String>();
		Integer brojIzabranihIgraca = Integer.valueOf(brojIgracaChoiceBox.getValue());
		for(int i = 0; i < Integer.valueOf(brojIgracaChoiceBox.getValue()); i++)
		{
			imena.add(igraciTextField.get(i).getText());
		}
		
		if(imena.stream().filter(i -> !i.isEmpty()).count()<brojIzabranihIgraca ||
				imena.stream().distinct().count() != brojIzabranihIgraca)
		{
			return false;
		}
		return true;
		
	}
	
	public List<String> getImenaIgraca()
	{
		List<String> imena = new ArrayList<String>();
		for(int i = 0; i < Integer.valueOf(brojIgracaChoiceBox.getValue()); i++)
		{
			imena.add(igraciTextField.get(i).getText());
		}
		return imena;
	}
}
