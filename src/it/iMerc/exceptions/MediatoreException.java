package it.iMerc.exceptions;

import javax.xml.namespace.QName;

import org.json.JSONObject;

public class MediatoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6601538967840235846L;
	
	public MediatoreException(String string) {
		super(string);
	}

	public JSONObject toJson() {
		return new JSONObject().put("exception", getClass().getName()).put("message", this.getMessage());
	}
	
	public QName getFaultCode() {
		return new QName(this.getClass().getName());
	}

}
