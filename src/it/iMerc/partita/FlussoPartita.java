package it.iMerc.partita;

public class FlussoPartita {
	
	//private List<Stato> allStati;
	private Stato statoCorrente;

	public static String STATO_INIZIALE = "sI";
	public static String STATO_CONFIGURAZIONE = "startConf";
	public static String STATO_CONFIGURAZIONE_END = "endConf";
	public static String STATO_MANO_CHIAMANTE = "startChiamata";
	public static String STATO_MONTE = "startMonte";
	public static String STATO_INIZIO_MANO = "startMano";
	public static String STATO_FINALE = "sF";


	public static Stato StatoFinale = new Stato(STATO_FINALE,null);
	public static Stato InizioMano = new Stato(STATO_INIZIO_MANO,StatoFinale);
	public static Stato Monte = new Stato(STATO_MONTE,InizioMano);
	public static Stato ManoChiamante = new Stato(STATO_MANO_CHIAMANTE,Monte);
	public static Stato ConfigurazioneEnd = new Stato(STATO_CONFIGURAZIONE_END,ManoChiamante);
	public static Stato Configurazione = new Stato(STATO_CONFIGURAZIONE,ConfigurazioneEnd);
	public static Stato StatoIniziale = new Stato(STATO_INIZIALE,Configurazione);
	
	public FlussoPartita() {
		statoCorrente = StatoIniziale;
	}
	
	public Stato getStatoCorrente() {
		return statoCorrente;
	}
	
	public void goNextStep() {
		System.out.println("to:" + statoCorrente.getNome());
		statoCorrente = getStatoCorrente().getNextStato();
		System.out.println("from:" + statoCorrente.getNome());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FlussoPartita))
			return super.equals(obj);
		else
			return this.statoCorrente.equals(((FlussoPartita)obj).getStatoCorrente());
	}
	
}
