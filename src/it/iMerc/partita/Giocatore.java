package it.iMerc.partita;

import java.io.Serializable;

import org.json.JSONObject;
import org.json.JSONPropertyName;

public class Giocatore implements Serializable {

	private static final long serialVersionUID = 8197106193737329087L;
	private Mazzo mano;
	private String nome;
	
	public Giocatore() {
		this.mano = new Mazzo();
	}
	
	public Giocatore(String nome) {
		this.mano = new Mazzo();
		this.nome = nome;
	}
	
	public void mettiInMano(Carta c) {
		mano.addCartaCoperta(c);
	}
	
	public Mazzo giocaCarta(Carta c) {
		int pos;
		for(pos = 0; pos < mano.getCarteInMano(); pos++ )
			if(c.equals(mano.getCarteCoperte().get(pos)))
				break;
		if(pos < mano.getCarteInMano())
			mano.consegnaCarta(pos);
		return mano;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	@JSONPropertyName("nome")
	public String getNome() {
		return nome;
	}
	
	@JSONPropertyName("mano")
	public Mazzo getMano() {
		return mano;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Giocatore) 
			return this.getNome().equals(((Giocatore)obj).getNome());
		return super.equals(obj);
	}
	
	public JSONObject toJson() {
		return new JSONObject(this);
	}

}
