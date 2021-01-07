package it.iMerc.partita;

import java.util.List;

import it.iMerc.exceptions.NumberOfPlayerException;

public interface Game {
	
	public Giocatore addGiocatore(Giocatore g);
	
	public boolean setMonte(boolean monte);
	
	public boolean possoDareCarte();
	
	public boolean possoDareMazzoAiGiocatori();

	public void daiCarte() throws NumberOfPlayerException;
	
	public Carta getTrionfo();
	
	public Mazzo getManoGiocatore(Giocatore g);
	
	public Giocatore getHost();
	
	public List<Giocatore> getGiocatori();
}
