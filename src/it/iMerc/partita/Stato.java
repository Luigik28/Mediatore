package it.iMerc.partita;

import java.util.LinkedList;
import java.util.List;

public class Stato implements Comparable<Stato>{
	
	private String nomeStato;
	private List<Stato> destinazioni = new LinkedList<Stato>();
	
	
	public Stato(String nomeStato) {
		this.destinazioni = new LinkedList<Stato>();
		this.nomeStato = nomeStato;
	}
	
	public String getNome() {
		return nomeStato;
	}
	
	public List<Stato> getDestinazioni() {
		return destinazioni;
	}
	
	public void addDestinazione(Stato s) {
		destinazioni.add(s);
	}
	
	public boolean canChangeto(Stato stato) {
		return destinazioni.contains(stato);
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
