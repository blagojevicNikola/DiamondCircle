package etf.unibl.org.models;

import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class TimerIgre implements Runnable {
	
	Integer vrijeme = 0;
	Label timerLabel;
	private volatile boolean exit = false;
	private Object lockObj = new Object();
	private AtomicBoolean pauzirano;
	
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
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

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
