package it.iMerc.partita;

import java.util.LinkedList;
import java.util.List;

import it.iMerc.exceptions.MediatoreException;
import it.iMerc.exceptions.NumberOfPlayerException;
import it.iMerc.util.Utility;

public class Partita implements Game {

	private Mazzo mazzo;
	private List<Giocatore> giocatori;
	private Giocatore host;
	private Carta trionfo;
	private FlussoPartita flusso;
	private boolean monte;
	private int primoDiMano = 0;

	public Partita(FlussoPartita f, Giocatore host) {
		mazzo = Utility.getMazzoIniziale();
		giocatori = new LinkedList<Giocatore>();
		this.host = host;
		addGiocatore(this.host);
		flusso = f;
		flusso.goNextStep();
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
		return this.monte = monte;
	}

	@Override
	public void daiCarte() throws MediatoreException {
		int cartePerGiocatore = Utility.getNumeroCarteIniziale(giocatori.size(), monte);
		for (Giocatore g : giocatori) 
			for (int i = 0; i < cartePerGiocatore; i++)
				g.mettiInMano(mazzo.getCartaRandom());
		trionfo = mazzo.getTrionfo();
		for (Giocatore g : giocatori)
			g.ordinaCarte(trionfo);
		if(!flusso.getStatoCorrente().equals(FlussoPartita.ConfigurazioneEnd))
			throw new MediatoreException("Errore nel flusso della partita");
		flusso.goNextStep();
	}

	@Override
	public boolean possoDareMazzoAiGiocatori() {
		if (flusso.getStatoCorrente().equals(FlussoPartita.ManoChiamante))
			return true;
		else {
			// nel caso la configurazione non sia terminata, aspetto 2 secondi e ricontrollo
			try {
				Thread.sleep(2000);
				if (flusso.getStatoCorrente().equals(FlussoPartita.ManoChiamante))
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

	@Override
	public void startGame() throws MediatoreException {
		if(giocatori.size() < 3)
			throw new NumberOfPlayerException("Ci sono meno di 3 giocatori");
		if(giocatori.size() > 5)
			throw new NumberOfPlayerException("Ci sono più di 5 giocatori");
		if(!flusso.getStatoCorrente().equals(FlussoPartita.Configurazione))
			throw new MediatoreException("Configurazione non terminata");
		flusso.goNextStep();
	}

}
