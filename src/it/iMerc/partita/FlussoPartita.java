package it.iMerc.partita;

import java.util.List;

public class FlussoPartita {
	
	//private List<Stato> allStati;
	private Stato statoCorrente;

	public static String STATO_INIZIALE = "sI";
	public static String STATO_CONFIGURAZIONE= "startConf";
	public static String STATO_END_CONFIGURAZIONE= "endConf";
	public static String STATO_INIZIO_MANO = "startMano";
	
	
	public FlussoPartita() {
		//allStati = new LinkedList<Stato>();
		//TODO: improvements
		//definisco tutti gli stati
		Stato statoIniziale = new Stato(STATO_INIZIALE);
		Stato setInizioConfigurazione = new Stato(STATO_CONFIGURAZIONE);
		Stato setFineConfigurazione = new Stato(STATO_END_CONFIGURAZIONE);
		Stato inizioMano = new Stato(STATO_INIZIO_MANO);
		//definisco le destinazioni
		statoIniziale.addDestinazione(setInizioConfigurazione);
		setInizioConfigurazione.addDestinazione(setFineConfigurazione);
		setFineConfigurazione.addDestinazione(inizioMano);
		//go!
		statoCorrente = statoIniziale;
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
	
	
	
}
