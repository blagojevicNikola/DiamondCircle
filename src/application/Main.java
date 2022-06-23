package application;
	
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	public static Handler handler;
	
	{
		try
		{
			handler = new FileHandler("Main.log");
			Logger.getLogger(Main.class.getName()).addHandler(handler);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("/etf/unibl/org/views/FirstPageView.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			Scene scene = new Scene(root);
			primaryStage.setResizable(false);
			primaryStage.setTitle("DiamondCircle");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
