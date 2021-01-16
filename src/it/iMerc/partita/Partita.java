package it.iMerc.partita;

import java.util.LinkedList;
import java.util.List;

import it.iMerc.exceptions.MediatoreException;
import it.iMerc.exceptions.NumberOfPlayerException;
import it.iMerc.util.Utility;

public class Partita implements Game {

	public static final int TIMEOUT = 0;
	public static final int PASSA = -1;
	public static final int CHIAMA = -2;
	public static final int SOLA = -3;
	public static final int MONTE_PRONTO = -4;

	private Mazzo mazzo;
	private List<Giocatore> giocatori;
	private Giocatore host;
	private Carta trionfo;
	private FlussoPartita flusso;
	private boolean bMonte;
	private int primoDiMano = 0;
	private int currentPlaying = -1;
	private LinkedList<Carta> monte = new LinkedList<Carta>();

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

	public void setMonte(boolean monte) {
		this.bMonte = monte;
	}
	
	public void setCarteMonte(LinkedList<Carta> monte) {
		this.monte = monte;
	}

	@Override
	public void daiCarte() throws MediatoreException {
		int cartePerGiocatore = Utility.getNumeroCarteIniziale(giocatori.size(), bMonte);
		for (Giocatore g : giocatori) 
			for (int i = 0; i < cartePerGiocatore; i++)
				g.mettiInMano(mazzo.getCartaRandom());
		trionfo = mazzo.getTrionfo();
		monte.add(trionfo);
		while (mazzo.stannoAncoraCarte())
			monte.add(mazzo.getCartaRandom());
		for (Giocatore g : giocatori)
			g.ordinaCarte(trionfo);
		if(!flusso.getStatoCorrente().equals(FlussoPartita.ConfigurazioneEnd))
			throw new MediatoreException("Errore nel flusso della partita");
		flusso.goNextStep();
	}
	
	public LinkedList<Carta> getMonte() {
		return monte;
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
	
	public Giocatore getGiocatoreAttivo() {
		return getGiocatori().get(getCurrentPlaying());
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
		setCurrentPlaying(primoDiMano);
	}
	
	private void setActivePlayer(int giocatore) {
		for(int i = 0; i < giocatori.size(); i++)
			giocatori.get(i).setActive(i == giocatore);
	}

	public int getCurrentPlaying() {
		return currentPlaying;
	}

	public void setCurrentPlaying(int currentPlaying) {
		this.currentPlaying = currentPlaying;
		setActivePlayer(getCurrentPlaying());
		getGiocatoreAttivo().setT0(System.currentTimeMillis());
	}
	
	public int nextPlayer() {
		if(currentPlaying == giocatori.size() - 1) {
			setCurrentPlaying(0);
			//Se ho finito il giro a chiamare cambio stato
			if(flusso.getStatoCorrente().equals(FlussoPartita.ManoChiamante))
				flusso.goNextStep();
		}
		else
			setCurrentPlaying(getCurrentPlaying() + 1);
		return getCurrentPlaying();
	}

	public int timeout() {
		if(flusso.getStatoCorrente().equals(FlussoPartita.ManoChiamante))
			return Partita.PASSA;
		else
			return 1;
	}
	
	public void chiama() {
		getGiocatoreAttivo().setChiamante(true);
		this.flusso.goNextStep();
	}

	public void updateGiocatori() {
		if(getGiocatoreAttivo().getTempoRimasto() < 0)
			gioca(Partita.TIMEOUT);
	}
	
	public int gioca(int mossa) {
		if(mossa == Partita.TIMEOUT)
			mossa = timeout();
		switch(mossa) {
		case Partita.PASSA:
			nextPlayer();
			break;
		case Partita.CHIAMA:
			chiama();
			break;
		case Partita.SOLA:
			break;
		case Partita.MONTE_PRONTO:
			this.flusso.goNextStep();
			//TODO setgiocatoreattivo il primo di mano
			break;
		default:
			getGiocatoreAttivo().giocaCarta(new Carta(mossa));
			nextPlayer();
			break;
		}
		getGiocatoreAttivo().setUltimaMossa(mossa);
		return getCurrentPlaying();
	}

}
