package it.iMerc.partita;

import java.util.LinkedList;
import java.util.List;

import it.iMerc.exceptions.NumberOfPlayerException;
import it.iMerc.util.Utility;

public class Partita implements Game {

	private Mazzo mazzo;
	private List<Giocatore> giocatori;
	private Giocatore host;
	private Carta trionfo;
	private FlussoPartita flusso;
	private boolean monte;

	public Partita(FlussoPartita f, Giocatore host) {
		mazzo = Utility.getMazzoIniziale();
		giocatori = new LinkedList<Giocatore>();
		this.host = host;
		addGiocatore(this.host);
		flusso = f;
		flusso.changeStato(new Stato(FlussoPartita.STATO_CONFIGURAZIONE));
	}

	@Override
	public Carta getTrionfo() {
		return trionfo;
	}

	@Override
	public Giocatore addGiocatore(Giocatore g) {
		giocatori.add(g);
		return g;
	}

	public List<Giocatore> getGiocatori() {
		return giocatori;
	}

	@Override
	public boolean setMonte(boolean monte) {
		flusso.changeStato(new Stato(FlussoPartita.STATO_END_CONFIGURAZIONE));
		return this.monte = monte;
	}

	@Override
	public void daiCarte() throws NumberOfPlayerException {
		int cartePerGiocatore = Utility.getNumeroCarteIniziale(giocatori.size(), monte);
		for (Giocatore g : giocatori)
			for (int i = 0; i < cartePerGiocatore; i++)
				g.mettiInMano(mazzo.getCartaRandom());
		trionfo = mazzo.getTrionfo();
		flusso.changeStato(new Stato(FlussoPartita.STATO_INIZIO_MANO));
	}

	@Override
	public boolean possoDareCarte() {
		return (flusso.getStatoCorrente().equals(new Stato(FlussoPartita.STATO_END_CONFIGURAZIONE))
				&& flusso.nextSteps().contains(new Stato(FlussoPartita.STATO_INIZIO_MANO)));
		// return true;
	}

	@Override
	public boolean possoDareMazzoAiGiocatori() {
		if (flusso.getStatoCorrente().equals(new Stato(FlussoPartita.STATO_INIZIO_MANO)))
			return true;
		else {
			// nel caso la configurazione non sia terminata, aspetto 2 secondi e ricontrollo
			try {
				Thread.sleep(2000);
				if (flusso.getStatoCorrente().equals(new Stato(FlussoPartita.STATO_INIZIO_MANO)))
					return true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	@Override
	public Mazzo getManoGiocatore(Giocatore g) {
		return giocatori.get(giocatori.indexOf(g)).getMano();
	}

	public void stampa() {
		for (Giocatore g : giocatori) {
			System.out.println(g.getNome() + " ha le carte:");
			for (Carta c : g.getMano().getCarteCoperte())
				System.out.println(c);
		}

		System.out.println("Trionfo: " + trionfo);
	}

	@Override
	public Giocatore getHost() {
		return host;
	}

}
