package be.ipl.finito.ucc;

import java.util.List;

import javax.ejb.Remote;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.domaine.Plateau;

/**
 * Cas d'utilisation pour la gestion des cases
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@Remote
public interface GestionPlateau {

	/**
	 * Cr�e un plateau d'un joueur pour une partie
	 * 
	 * @param joueur
	 * @param partie
	 * @return le plateau
	 */
	Plateau creerPlateau(Joueur joueur, Partie partie);

	/**
	 * Place un jeton dans une case d'un plateau
	 * 
	 * @param plateau
	 * @param jeton
	 * @param caseCible
	 * @return true si le placement du jeton s'est bien effectu�
	 */
	boolean placerJeton(Plateau plateau, Jeton jeton, Case caseCible);

	/**
	 * D�place dans un plateau un jeton d'une case "source" vers une case
	 * "destination"
	 * 
	 * @param plateau
	 * @param caseSource
	 * @param caseDestination
	 * @return true si le d�placement du jeton s'est bien effectu�
	 */
	boolean deplacerJeton(Plateau plateau, Case caseSource, Case caseDestination);

	/**
	 * Recherche un plateau � partir de l'id de la partie et de l'id du joueur
	 * 
	 * @param idPartie
	 * @param idJoueur
	 * @return le plateau
	 */
	Plateau recherchePlateauPourJoueurEtPartie(int idPartie, int idJoueur);

	/**
	 * Renvoie la liste des cases pour un plateau
	 * 
	 * @param plateau
	 * @return la liste des cases pour un plateau
	 */
	List<Case> recupererLaListeDesCases(Plateau plateau);

	/**
	 * Renvoie la liste des jetons en main d'un joueur via son plateau
	 * 
	 * @param plateau
	 * @return la liste des jetons en main
	 */
	public List<Jeton> recupererMainPlateau(Plateau plateau);

	/**
	 * Renvoie la liste des cases libres d'un plateau de joueur en fonction du
	 * num�ro de d�
	 * 
	 * @param plateau
	 * @param de
	 * @return la liste des cases libres
	 */
	List<Case> recupererLesCasesLibres(Plateau plateau, int de);
}
