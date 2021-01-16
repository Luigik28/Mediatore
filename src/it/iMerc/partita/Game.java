package it.iMerc.partita;

import java.util.List;

import it.iMerc.exceptions.MediatoreException;

public interface Game {
	
	public Giocatore addGiocatore(Giocatore g);
	
	public boolean possoDareMazzoAiGiocatori();

	public void daiCarte() throws MediatoreException;
	
	public Carta getTrionfo();
	
	public Mazzo getManoGiocatore(Giocatore g);
	
	public Giocatore getHost();
	
	public List<Giocatore> getGiocatori();
	
	public void startGame() throws MediatoreException;
	
}
