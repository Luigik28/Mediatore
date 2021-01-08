package it.iMerc.partita;

public class Stato implements Comparable<Stato>{
	
	private String nomeStato;
	private Stato destinazione;
	
	
	public Stato(String nomeStato, Stato next) {
		this.destinazione = next;
		this.nomeStato = nomeStato;
	}
	
	public String getNome() {
		return nomeStato;
	}
	
	public Stato getNextStato() {
		return destinazione;
	}
	
	@Override
	public int compareTo(Stato o) {
		return this.nomeStato.compareTo(o.getNome());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Stato)
			return nomeStato.equals(((Stato)obj).getNome());
		return super.equals(obj);
	}
}
