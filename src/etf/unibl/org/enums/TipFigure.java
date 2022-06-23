package etf.unibl.org.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum TipFigure {
	SUPERBRZA,
	OBICNA,
	LEBDECA;
	
	public static TipFigure getRandomTipFigure()
	{
		Random rnd = new Random();
		TipFigure[] listaTipova = TipFigure.values();
		int i = rnd.nextInt(3);
		return listaTipova[i];
	}
}
