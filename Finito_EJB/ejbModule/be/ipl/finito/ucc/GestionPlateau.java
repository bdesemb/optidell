package be.ipl.finito.ucc;

import javax.ejb.Remote;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Plateau;

@Remote
public interface GestionPlateau {

	Plateau creerPlateau(Joueur joueur);
	boolean placerJeton(Plateau plateau,Jeton jeton, Case caseCible);
	boolean deplacerJeton(Plateau plateau, Case caseDepart, Case caseCible);
}
