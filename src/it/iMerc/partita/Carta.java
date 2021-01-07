package it.iMerc.partita;

import java.io.Serializable;

import org.json.JSONObject;
import org.json.JSONPropertyName;

public class Carta implements Comparable<Carta>, Serializable {

	private static final long serialVersionUID = 1341106024248870488L;
	private int id;
	private boolean consegnata = false;
	private boolean giocata = false;
	
	public Carta() {
	}
	
	
	public Carta(int id) {
		this.id = id;
	}
	
	@JSONPropertyName("id")
	public int getId() {
		return id;
	}
	
	public int getNumero() {
		int n = id%10;
		return n == 0 ? 10 : n;
	}
	
	public String getSeme() {
		String tipo = "";
		switch ((id - 1)/10) {
			case 0:
				tipo = "denare";
				break;
			case 1:
				tipo = "coppe";
				break;
			case 2:
				tipo = "mazze";
				break;
			case 3:
				tipo = "spade";
				break;
		}
		return tipo;
	}

	@JSONPropertyName("consegnata")
	public boolean isConsegnata() {
		return consegnata;
	}

	public void setConsegnata(boolean consegnata) {
		this.consegnata = consegnata;
	}

	@JSONPropertyName("giocata")
	public boolean isGiocata() {
		return giocata;
	}

	public void setGiocata(boolean giocata) {
		this.giocata = giocata;
	}
	
	@Override
	public String toString() {
		return getNumero() + " di " + getSeme();
	}

	@Override
	public int compareTo(Carta c) {
		return Integer.valueOf(getId()).compareTo(c.getId());
	}
	
	public JSONObject toJson() {
		return new JSONObject(this);
	}

}
