package it.iMerc.partita;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONObject;
import org.json.JSONPropertyName;

public class Giocatore implements Serializable {

	private static final long serialVersionUID = 8197106193737329087L;
	private Mazzo mano;
	private String nome;
	private long t0 = 0;
	private int TEMPO_MAX = 20000;
	private boolean chiamante = false;
	
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
	
	@Override
	public String toString() {
		return this.toJson().toString();
	}

	public void ordinaCarte(Carta trionfo) {
		Collections.sort(mano.getCarteCoperte(), new Comparator<Carta>() {

			@Override
			public int compare(Carta o1, Carta o2) {
				//Seme di trionfo in testa
				if(o1.getSeme().equals(trionfo.getSeme()) && !o2.getSeme().equals(trionfo.getSeme()))
					return -1;
				if(o2.getSeme().equals(trionfo.getSeme()) && !o1.getSeme().equals(trionfo.getSeme()))
					return 1;
				//Ordino per seme
				if(!o1.getSeme().equals(o2.getSeme()))
					return o1.getId() - o2.getId();
				else {
					if(o1.getNumero() == 7)
						return -1;
					if(o2.getNumero() == 7)
						return 1;
					if(o1.getNumero() == 1)
						return -1;
					if(o2.getNumero() == 1)
						return 1;
					return Integer.compare(o2.getNumero(), o1.getNumero());
				}
			}
		});
	}
	
	public void setT0(long milli) {
		t0 = milli;
	}
	
	public int getTempoRimasto() {
		long now = System.currentTimeMillis();
		return (int) (TEMPO_MAX - (now - t0));
	}

	public boolean isChiamante() {
		return chiamante;
	}

	public void setChiamante(boolean chiamante) {
		this.chiamante = chiamante;
	}

}
