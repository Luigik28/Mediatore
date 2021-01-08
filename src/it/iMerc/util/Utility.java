package it.iMerc.util;

import it.iMerc.exceptions.NumberOfPlayerException;
import it.iMerc.exceptions.TooFewPlayerException;
import it.iMerc.exceptions.TooManyPlayersException;
import it.iMerc.partita.Carta;
import it.iMerc.partita.Mazzo;

public class Utility {

    //Ordine: denare coppe mazze spade
	
	public static Mazzo getMazzoIniziale() {
		Mazzo mazzo = new Mazzo();
		for(int i = 1; i <= 40; i++)
			mazzo.addCartaCoperta(new Carta(i));
		return mazzo;
	}
	
	//TODO: numero di carte distribuibili dipendentemente da giocatori e monte
	public static int getNumeroCarteIniziale(int giocatori, boolean monte) throws NumberOfPlayerException {
		int carte = giocatori == 5 ? 8 : 36/giocatori;
		if(giocatori < 3) 
			throw new TooFewPlayerException();
		if(giocatori > 5)
			throw new TooManyPlayersException();
		return carte;
	}
	
}
