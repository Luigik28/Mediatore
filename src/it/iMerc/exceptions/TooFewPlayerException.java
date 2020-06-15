package it.iMerc.exceptions;

public class TooFewPlayerException extends NumberOfPlayerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1120393320534600248L;
	
	public TooFewPlayerException() {
		super("Pochi giocatori censiti");
	}

}
