package it.iMerc.exceptions;

public class NoGameFoundException extends MediatoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -154449932185326260L;
	
	public NoGameFoundException() {
		super("ID partita non valido");
	}

}
