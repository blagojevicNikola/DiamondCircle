package etf.unibl.org.views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import etf.unibl.org.config.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DijamantWidget extends ImageView {
	
	public DijamantWidget() throws FileNotFoundException
	{
		super(new Image(new FileInputStream(Config.putanjaSlikeDijamanta), 40,40,false,false));
//		super.maxHeight(30);
//		super.maxWidth(30);
	}

}
