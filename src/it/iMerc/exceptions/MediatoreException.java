package it.iMerc.exceptions;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.json.JSONObject;

public class MediatoreException extends AxisFault {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6601538967840235846L;
	
	public MediatoreException(String string) {
		super(string);
	}

	public JSONObject toJson() {
		return new JSONObject().put("exception", getClass().toString()).put("message", this.getMessage());
	}
	
	@Override
	public QName getFaultCode() {
		return new QName(this.getClass().getName());
	}
	
	@Override
	public String toString() {
		return toJson().toString();
	}

}
