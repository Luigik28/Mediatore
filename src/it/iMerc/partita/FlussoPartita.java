package it.iMerc.partita;

import java.util.List;

public class FlussoPartita {
	
	//private List<Stato> allStati;
	private Stato statoCorrente;

	public static String STATO_INIZIALE = "sI";
	public static String STATO_CONFIGURAZIONE= "startConf";
	public static String STATO_END_CONFIGURAZIONE= "endConf";
	public static String STATO_INIZIO_MANO = "startMano";

	public static Stato StatoIniziale = new Stato(STATO_INIZIALE);
	public static Stato InizioConfigurazione = new Stato(STATO_CONFIGURAZIONE);
	public static Stato FineConfigurazione = new Stato(STATO_END_CONFIGURAZIONE);
	public static Stato InizioMano = new Stato(STATO_INIZIO_MANO);
	
	static {
		StatoIniziale.addDestinazione(InizioConfigurazione);
		InizioConfigurazione.addDestinazione(FineConfigurazione);
		FineConfigurazione.addDestinazione(InizioMano);
	}
	
	public FlussoPartita() {
		//allStati = new LinkedList<Stato>();
		//TODO: improvements
		statoCorrente = StatoIniziale;
	}
	
	public Stato getStatoCorrente() {
		return statoCorrente;
	}
	
	public List<Stato> nextSteps() {
		return statoCorrente.getDestinazioni();
	}
	
	public boolean changeStato(Stato s) {
		boolean cambio = true;
		if(nextSteps().contains(s))
			statoCorrente = nextSteps().get(nextSteps().indexOf(s));
		else
			cambio = false;
		return cambio;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FlussoPartita))
			return super.equals(obj);
		else
			return this.statoCorrente.equals(((FlussoPartita)obj).getStatoCorrente());
	}
	
}
