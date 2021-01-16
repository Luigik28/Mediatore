package it.iMerc.partita;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONPropertyIgnore;
import org.json.JSONPropertyName;

public class Mazzo implements Serializable {

	private static final long serialVersionUID = -2966124301631969484L;
	private List<Carta> carteCoperte = new LinkedList<Carta>();
	private List<Carta> carteConsegnate = new LinkedList<Carta>();

	@JSONPropertyName("carteCoperte")
	public List<Carta> getCarteCoperte() {
		return carteCoperte;
	}

	@JSONPropertyName("carteConsegnate")
	public List<Carta> getCarteConsegnate() {
		return carteConsegnate;
	}

	public void addCartaCoperta(Carta c) {
		carteCoperte.add(c);
	}

	public Carta consegnaCarta(int pos) {
		Carta c = carteCoperte.remove(pos);
		carteConsegnate.add(c);
		return c;
	}

	@JSONPropertyIgnore
	public int getCarteInMano() {
		return getCarteCoperte().size();
	}

	@JSONPropertyIgnore
	public Carta getCartaRandom() {
		if (getCarteInMano() > 0)
			return consegnaCarta(new Random().nextInt(getCarteInMano()));
		else
			return null;
	}

	@JSONPropertyIgnore
	public Carta getTrionfo() {
		if (getCarteInMano() > 0)
			return consegnaCarta(new Random().nextInt(getCarteInMano()));
		else
			return null;
	}
	
	@JSONPropertyIgnore
	public String getMazzoBean() {
		String bean = "";
		for(Carta c : getCarteCoperte()) {
			bean += c.getId() + ";";
		}
		return bean.substring(0, bean.length()-1);
	}
	
	public boolean stannoAncoraCarte() {
		return carteCoperte.size() > 0;
	}
	
	public JSONObject toJson() {
		return new JSONObject(this);
	}

}
