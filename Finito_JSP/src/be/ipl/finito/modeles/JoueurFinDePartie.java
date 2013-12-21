package be.ipl.finito.modeles;

import be.ipl.finito.domaine.Joueur;

/**
 * Classe utile en fin de partie afin de transmettre à la page JSP
 * les joueurs de la partie et leur score respectif
 */
public class JoueurFinDePartie {
	
	private Joueur joueur;
	private int score;
	private int[][] jetons = new int[6][6];
	private int[][] cases = new int[6][6];
	
	public JoueurFinDePartie(Joueur joueur, int score) {
		this.joueur = joueur;
		this.score = score;
	}
	
	
	public Joueur getJoueur() {
		return joueur;
	}
	public int getScore() {
		return score;
	}
	
	public void setJeton(int i, int j, int x) {
		jetons[i][j] = x;
	}
	
	
	public int getJeton(int i, int j) {
		return jetons[i][j];
	}


	public int getCase(int i, int j) {
		return cases[i][j];
	}


	public void setCase(int i, int j, int x) {
		this.cases[i][j] = x;
	}
	
}
