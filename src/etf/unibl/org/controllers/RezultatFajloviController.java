package etf.unibl.org.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class RezultatFajloviController {
	
	@FXML private ListView<String> listaViewFajlovi;
	@FXML private TextArea textAreaSadrzajFajla;
	
	public RezultatFajloviController()
	{
		
	}
	
	public void pripremi()
	{
		
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
		
		File fajlovi = new File(prop.getProperty("rezultati"));
		String listaFajlova[] = fajlovi.list();
		listaViewFajlovi.getItems().addAll(listaFajlova);
		listaViewFajlovi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
				// TODO Auto-generated method stub
				File fajlTemp = new File(prop.getProperty("rezultati")+listaViewFajlovi.getSelectionModel().getSelectedItem());
				FileInputStream fis = null;
				String str = "";
				try {
					fis = new FileInputStream(fajlTemp);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] data = new byte[(int) fajlTemp.length()];
				try {
					fis.read(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					str = new String(data, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				textAreaSadrzajFajla.setText(str);
			}
			
			
		});
		
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
