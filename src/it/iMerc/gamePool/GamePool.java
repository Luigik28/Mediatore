package it.iMerc.gamePool;

import java.util.HashMap;
import java.util.Map;

import it.iMerc.exceptions.NoGameFoundException;
import it.iMerc.partita.FlussoPartita;
import it.iMerc.partita.Game;
import it.iMerc.partita.Giocatore;
import it.iMerc.partita.Partita;

public class GamePool {
	
	private static Map<Integer, Game> pool = new HashMap<Integer, Game>();
	private static int id = 0;
	
	public static int addGame(Game g) {
		int id = getNextId();
		System.out.println("Creata partita id: " + id);
		pool.put(id, g);
		return id;
	}
	
	public static int createNewGame(String host) {
		return addGame(new Partita(new FlussoPartita(), new Giocatore(host)));
	}
	
	public static Game getGame(int key) throws NoGameFoundException {
		Game g = pool.get(key);
		if(g == null) {
			throw new NoGameFoundException();
		}
		return g;
	}
	
	private static int getNextId() {
		return ++id;
	}
	

}
