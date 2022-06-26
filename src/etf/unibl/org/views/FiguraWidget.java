package etf.unibl.org.views;

import etf.unibl.org.config.Config;
import etf.unibl.org.models.interfaces.Figura;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class FiguraWidget extends StackPane {

	
	public FiguraWidget(Color boja, String oznaka)
	{
		super(new Rectangle(Config.VELICINA_FIGURE,Config.VELICINA_FIGURE, boja));
		Label tekst = new Label(oznaka);
		tekst.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");
		super.getChildren().add(tekst);
	}
}
