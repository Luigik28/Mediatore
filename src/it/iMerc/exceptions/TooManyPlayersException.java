package it.iMerc.exceptions;

public class TooManyPlayersException extends NumberOfPlayerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2631849630630856481L;
	
	public TooManyPlayersException() {
		super("Troppi giocatori censiti");
	}

}
