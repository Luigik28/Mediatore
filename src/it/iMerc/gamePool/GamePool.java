package it.iMerc.gamePool;

import java.util.HashMap;
import java.util.Map;

import it.iMerc.exceptions.NoGameFoundException;
import it.iMerc.partita.FlussoPartita;
import it.iMerc.partita.Game;
import it.iMerc.partita.Giocatore;
import it.iMerc.partita.Partita;

public class GamePool {
	
	private static Map<Object, Game> pool = new HashMap<Object, Game>();
	private static int id = 0;
	
	public static String addGame(Game g) {
		int id = getNextId();
		System.out.println("Creata partita id: " + id);
		pool.put(id, g);
		return String.valueOf(id);
	}
	
	public static String addGame(String key, Game g) {
		pool.put(key, g);
		System.out.println("Creata partita id: " + key);
		return key;
	}
	
	public static String createNewGame(String name, String host) {
		return addGame(name, new Partita(new FlussoPartita(), new Giocatore(host)));
	}
	
	public static String createNewGame(String host) {
		return addGame(new Partita(new FlussoPartita(), new Giocatore(host)));
	}
	
	public static Game getGame(Object key) throws NoGameFoundException {
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
