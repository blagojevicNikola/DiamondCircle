package etf.unibl.org.views;

import java.io.FileNotFoundException;
import java.io.IOException;

import etf.unibl.org.config.Config;
import etf.unibl.org.models.Polje;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PoljeWidget extends StackPane {
	
	FiguraWidget sadrzaniWidget;
	Rectangle podloga;
	DijamantWidget dijamantWidget;
	
	public PoljeWidget()
	{
		super();
	}
	
	public PoljeWidget(String oznaka)
	{
		super();
		this.podloga = new Rectangle(Config.VELICINA_POLJA, Config.VELICINA_POLJA);
		this.podloga.setFill(Color.DIMGRAY);
		super.getChildren().addAll(this.podloga,new Label(String.valueOf(oznaka)));
		sadrzaniWidget = null;
	}
	
	public void postaviFiguraWidget(Color boja, String oznaka)
	{
		sadrzaniWidget = new FiguraWidget(boja, oznaka);
		PoljeWidget p = this;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				p.getChildren().addAll(sadrzaniWidget);
			}});
//	    this.getChildren().add(f);
			
	}
	
	public void ukloniFiguraWidget()
	{
		PoljeWidget p = this;
		FiguraWidget temp = sadrzaniWidget;
		sadrzaniWidget = null;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
			
				p.getChildren().remove(temp);
				
			}});
		
		//sadrzaniWidget = null;
		//this.getChildren().remove(sadrzaniWidget);
	}
	
	public void postaviCrnuRupu(Boolean figuraLebdi)
	{
//		PoljeWidget p = this;
//		crnaRupaWidget = new CrnaRupaWidget();
//		Platform.runLater(new Runnable() {
//
//			@Override
//			public void run() {
//				if(sadrzaniWidget!=null)
//				{
//					p.getChildren().remove(sadrzaniWidget);
//				}
//				p.getChildren().addAll(crnaRupaWidget);
//				
//			}});
		//sadrzaniWidget = null;
		if(sadrzaniWidget!=null && figuraLebdi==false)
		{
			this.getChildren().remove(sadrzaniWidget);
			sadrzaniWidget=null;
		}
		//this.getChildren().addAll(crnaRupaWidget);
		this.podloga.setFill(Color.BLACK);
	}
	
	public void ukloniCrnuRupu()
	{
//		PoljeWidget p = this;
//		Platform.runLater(new Runnable() {
//
//			@Override
//			public void run() { 
//				
//				p.getChildren().remove(crnaRupaWidget);
//				
//			}});
//		crnaRupaWidget = null;
		
		//this.getChildren().remove(crnaRupaWidget);
		//this.setStyle("-fx-background-color: #696969;");
		this.podloga.setFill(Color.DIMGRAY);

	}
	
	public void postaviDijamant() throws IOException
	{
		DijamantWidget d = null;
		d = new DijamantWidget();
		this.dijamantWidget = d;
		this.getChildren().addAll(this.dijamantWidget);
	}
	
	public void ukloniDijamant()
	{
		if(this.dijamantWidget!=null)
		{
			this.getChildren().remove(this.dijamantWidget);
			this.dijamantWidget = null;
		}
		
	}
	

}
