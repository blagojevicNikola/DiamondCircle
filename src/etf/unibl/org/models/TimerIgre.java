package etf.unibl.org.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class TimerIgre implements Runnable {
	
	Integer vrijeme = 0;
	Label timerLabel;
	private volatile boolean exit = false;
	private Object lockObj = new Object();
	private AtomicBoolean pauzirano;
	
	public static FileHandler handler;
	
	static{
		try
		{
			Properties prop = new Properties();
			InputStream inputStream = new FileInputStream("resources/config/config.properties");
			prop.load(inputStream);
			handler = new FileHandler(prop.getProperty("logFolder") + "TimerIgre.log");
			Logger.getLogger(TimerIgre.class.getName()).addHandler(handler);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public TimerIgre(Label timerLabel, AtomicBoolean pauzirano, Object lockObj)
	{
		this.timerLabel = timerLabel;
		this.pauzirano = pauzirano;
		this.lockObj = lockObj;
	}
	
	
	@Override
	public void run() {
		while(!exit)
		{
			synchronized(lockObj)
			{
				if(pauzirano.get() == true)
				{
					try {
						lockObj.wait();
					} catch (InterruptedException e) {
						Logger.getLogger(TimerIgre.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
					}
				}
			}
			
			Platform.runLater(new Runnable()
					{

						@Override
						public void run() {
							timerLabel.setText(vrijeme.toString());
						}
				
					}
					);
			vrijeme++;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Logger.getLogger(TimerIgre.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
		handler.close();
	}
	
	public void resetTimer()
	{
		this.vrijeme = 0;
	}
	
	public void stopTimer()
	{
		this.exit = true;
	}

}
