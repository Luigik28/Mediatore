package it.iMerc.service;

import it.iMerc.exceptions.MediatoreException;
import it.iMerc.exceptions.NoGameFoundException;
import it.iMerc.exceptions.NumberOfPlayerException;
import it.iMerc.gamePool.GamePool;
import it.iMerc.partita.Game;
import it.iMerc.partita.Giocatore;
import it.iMerc.partita.Mazzo;
import it.iMerc.partita.Partita;

public class GameService {
	
	public String ciao(String n) {
		return n;
	}
	
	public String creaPartita(String partita, String giocatore) {
		if(partita != null)
			return GamePool.createNewGame(partita, giocatore);
		else
			return GamePool.createNewGame(giocatore);
	}
	
	public boolean esistePartita(String id) {
		try {
			GamePool.getGame(id);
		} catch (NoGameFoundException e) {
			return false;
		}
		return true;
	}
	
	public String addGiocatore(String id, String nome) throws NoGameFoundException {
		System.out.println("nuovo giocatore " + nome);
		return GamePool.getGame(id).addGiocatore(new Giocatore(nome)).toJson().toString();
	}
	
	public Boolean setMonte(String id, boolean monte) throws NoGameFoundException {
		System.out.println("set monte " + monte);
		return GamePool.getGame(id).setMonte(monte);
	}
	
	//smista le carte e da il mazzo all'host
	public String daiCarte(String id) throws MediatoreException {
		System.out.println("do carte");
		try {
			Game g = GamePool.getGame(id);
			g.daiCarte();
			return g.getManoGiocatore(g.getHost()).toJson().toString();
		} catch (NumberOfPlayerException e) {
			return e.toString();
		}
	}
	
	public String getMazzoGiocatore(String idGame, String player) throws MediatoreException {
		System.out.println("do carte al giocatore " + player);
		Game g = GamePool.getGame(idGame);
		Mazzo m = g.getManoGiocatore(new Giocatore(player));
		if(m == null)
			throw new MediatoreException("Nessun giocatore trovato");
		if(!g.possoDareMazzoAiGiocatori())
			throw new MediatoreException("L'host non ha compleato la configurazione");
		return m.toString();
	}
	
	public int numeroGiocatori(String idGame) throws NoGameFoundException {
		return GamePool.getGame(idGame).getGiocatori().size();
	}
	
	public String getGiocatori(String idGame) throws NoGameFoundException {
		return GamePool.getGame(idGame).getGiocatori().toString();
	}

	public String getTrionfo(String idGame) throws NoGameFoundException {
		return GamePool.getGame(idGame).getTrionfo().toJson().toString();
	}
	
	public boolean startGame(String idGame) throws NoGameFoundException {
		try {
			GamePool.getGame(idGame).startGame();
			return true;
		} catch (MediatoreException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int getRemainingTime(String idGame) throws NoGameFoundException {
		Partita p = (Partita) GamePool.getGame(idGame);
		Giocatore g = p.getGiocatori().get(p.getCurrentPlaying());
		return g.getTempoRimasto();
	}
	
	public int gioca(String idGame, int mossa) throws NoGameFoundException {
		return ((Partita) GamePool.getGame(idGame)).gioca(mossa);
	}
	
	public String updateGiocatori(String idGame) throws NoGameFoundException {
		((Partita) GamePool.getGame(idGame)).updateGiocatori();		
		return GamePool.getGame(idGame).getGiocatori().toString();
	}
}
