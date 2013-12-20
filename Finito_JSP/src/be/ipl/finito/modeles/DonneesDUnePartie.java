package be.ipl.finito.modeles;

import java.util.HashSet;
import java.util.Set;
import java.util.Timer;

public class DonneesDUnePartie {

	private int id;
	private String etat;
	private Set<Integer> joueurs;
	private Timer timer;
	
	public DonneesDUnePartie(int id) {
		super();
		this.id = id;
		joueurs = new HashSet<Integer>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public Set<Integer> getJoueurs() {
		return joueurs;
	}
	public void setJoueurs(Set<Integer> joueurs) {
		this.joueurs = joueurs;
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
}
