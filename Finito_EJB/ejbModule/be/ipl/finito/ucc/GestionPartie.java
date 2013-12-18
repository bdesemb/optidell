package be.ipl.finito.ucc;

import java.util.List;

import javax.ejb.Remote;

import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.domaine.Plateau;

@Remote
public interface GestionPartie {
	
	Partie creerPartie(Joueur joueur);
	Boolean ajouterJoueur(Partie partie, Joueur joueur);
	Jeton piocherJeton(Partie partie);
	int lancerDe(Partie partie);
	void suspendreJoueur(Partie partie, Plateau plateau);
	int[] finirPartie(Partie partie);
	void debuterPartie(Partie partie);
	void reprendreJoueur(Partie partie, Plateau plateau);
	List<Partie>listerPartiesEnAttente();
	List<Partie>listerPartiesEnSuspend(Joueur joueur);
	int nbrJoueurConnectes(Partie partie);
	Partie recupererPartieAvecID(int id);
}
