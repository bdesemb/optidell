package be.ipl.finito.modeles;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

public class DonneesDUnePartie {

	private int id;
	private String etat;
	private Timer timer;
	private int resultatDe;
	private int tour;
	private Set<Integer> joueurs;
	private Map<Integer, Integer> joueursNumTours;
	
	public DonneesDUnePartie(final int id) {
		super();
		this.id = id;
		joueurs = new HashSet<Integer>();
		joueursNumTours = new HashMap<Integer, Integer>();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public String getEtat() {
		return etat;
	}
	
	public void setEtat(final String etat) {
		this.etat = etat;
	}
	
	public int getResultatDe() {
		return resultatDe;
	}

	public void setResultatDe(final int resultatDe) {
		this.resultatDe = resultatDe;
	}

	public Set<Integer> getJoueurs() {
		return joueurs;
	}
	
	public void setJoueurs(final Set<Integer> joueurs) {
		this.joueurs = joueurs;
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void setTimer(final Timer timer) {
		this.timer = timer;
	}

	public int getTour() {
		return tour;
	}

	public void setTour(final int tour) {
		this.tour = tour;
	}

	public Map<Integer, Integer> getJoueursNumTours() {
		return joueursNumTours;
	}

	public void setJoueursNumTours(final Map<Integer, Integer> joueursNumTours) {
		this.joueursNumTours = joueursNumTours;
	}
}
