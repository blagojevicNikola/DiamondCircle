package etf.unibl.org.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import etf.unibl.org.config.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DijamantWidget extends ImageView {
	
	public DijamantWidget() throws IOException
	{
		super();
		Properties prop = new Properties();
		InputStream inputStream = new FileInputStream("resources/config/config.properties");
		prop.load(inputStream);
		Image slika = new Image(new FileInputStream(prop.getProperty("slikaDijamanta")), Config.VELICINA_DIJAMANTA, Config.VELICINA_DIJAMANTA,false,false);
		super.setImage(slika);
//		super.maxHeight(30);
//		super.maxWidth(30);
	}

}
