package etf.unibl.org.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import etf.unibl.org.models.interfaces.Figura;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class PredjenaPutanjaFormController {
	
	@FXML private StackPane predjenaPutanjaHolder;
	
	public void ucitajPutanjuFigure(Figura f, Integer dimenzija)
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
		
		File inputFile = new File(prop.getProperty("putanje")+ dimenzija +  ".txt");
		FileReader input = null;
		Scanner sc = null;

		try {
			input = new FileReader(inputFile);
			sc = new Scanner(input).useDelimiter(",");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Integer> listaPozicija = new ArrayList<Integer>();
		Integer pom = 0;
		while(sc.hasNext())
		{
			if(pom<=f.getIndexPosljednjegPolja()){
				listaPozicija.add(sc.nextInt());
			}
			else
			{
				break;
			}
			pom++;
		}
		sc.close();

		GridPane legendaPane = new GridPane();
		legendaPane.setAlignment(Pos.CENTER);
		legendaPane.setVgap(2);
		legendaPane.setHgap(2);
		for(int i = 0; i < dimenzija; i++)
		{
			for(int j = 0; j < dimenzija; j++)
			{
				StackPane polje = new StackPane();
				polje.setMinHeight(45);
				polje.setMinWidth(45);
				Integer oznaka = j*dimenzija+i+1;
				if(listaPozicija.contains(oznaka))
				{
					polje.setStyle("-fx-background-color:#ff3333");
				}
				else
				{
					polje.setStyle("-fx-background-color:#DCDCDC");
				}
				Label tekstOznake = new Label(oznaka.toString());
				tekstOznake.setAlignment(Pos.CENTER);
				tekstOznake.setTextAlignment(TextAlignment.RIGHT);
				tekstOznake.setFont(new Font("Arial", 15));
				polje.getChildren().add(new Label(oznaka.toString()));
				legendaPane.add(polje, i, j);
			}
		}
		this.predjenaPutanjaHolder.getChildren().add(legendaPane);
		
		try {
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
