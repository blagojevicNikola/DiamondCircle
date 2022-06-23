package etf.unibl.org.views;

import java.io.FileNotFoundException;

import etf.unibl.org.models.Polje;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PoljeWidget extends StackPane {
	
	FiguraWidget sadrzaniWidget;
	CrnaRupaWidget crnaRupaWidget;
	DijamantWidget dijamantWidget;
	
	public PoljeWidget()
	{
		super();
	}
	
	public PoljeWidget(String oznaka)
	{
		super(new Rectangle(55, 55, Color.DIMGRAY), new Label(String.valueOf(oznaka)));
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
		crnaRupaWidget = new CrnaRupaWidget();
		if(sadrzaniWidget!=null && figuraLebdi==false)
		{
			this.getChildren().remove(sadrzaniWidget);
			sadrzaniWidget=null;
		}
		this.getChildren().addAll(crnaRupaWidget);
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
		
		this.getChildren().remove(crnaRupaWidget);
		crnaRupaWidget = null;
	}
	
	public void postaviDijamant() throws FileNotFoundException
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
