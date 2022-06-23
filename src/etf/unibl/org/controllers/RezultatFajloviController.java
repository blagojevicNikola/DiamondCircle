package etf.unibl.org.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class RezultatFajloviController {
	
	@FXML private ListView<String> listaViewFajlovi;
	@FXML private TextArea textAreaSadrzajFajla;
	
	public static Handler handler;
	
	{
		try {
			handler = new FileHandler("RezultatFajloviController.log");
			Logger.getLogger(RezultatFajloviController.class.getName()).addHandler(handler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public RezultatFajloviController()
	{
		
	}
	
	public void pripremi() throws IOException
	{
		
		Properties prop = new Properties();
		InputStream inputStream = null;
		
		inputStream = new FileInputStream("resources/config/config.properties");
		
		
		prop.load(inputStream);
		
		
		File fajlovi = new File(prop.getProperty("rezultati"));
		String listaFajlova[] = fajlovi.list();
		listaViewFajlovi.getItems().addAll(listaFajlova);
		listaViewFajlovi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
			
				File fajlTemp = new File(prop.getProperty("rezultati")+listaViewFajlovi.getSelectionModel().getSelectedItem());
				FileInputStream fis = null;
				String str = "";
				try {
					fis = new FileInputStream(fajlTemp);
				} catch (FileNotFoundException e) {
					Logger.getLogger(RezultatFajloviController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
				byte[] data = new byte[(int) fajlTemp.length()];
				try {
					fis.read(data);
					fis.close();
				} catch (IOException e) {
					Logger.getLogger(RezultatFajloviController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());	
				}
				
				try {
					str = new String(data, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					Logger.getLogger(RezultatFajloviController.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());	
				}
				
				textAreaSadrzajFajla.setText(str);
			}
			
			
		});
		
		
		inputStream.close();
		
	}
	
}
