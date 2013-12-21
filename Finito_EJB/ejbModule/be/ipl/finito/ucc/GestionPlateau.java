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
	 * Crée un plateau d'un joueur pour une partie
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
	 * @param caseDestination
	 * @return true si le placement du jeton s'est bien effectué
	 */
	boolean placerJeton(Plateau plateau, Jeton jeton, Case caseDestination);

	/**
	 * Déplace dans un plateau un jeton d'une case "source" vers une case
	 * "destination"
	 * 
	 * @param plateau
	 * @param caseSource
	 * @param caseDestination
	 * @return true si le déplacement du jeton s'est bien effectué
	 */
	boolean deplacerJeton(Plateau plateau, Case caseSource, Case caseDestination);

	/**
	 * Recherche un plateau à partir de l'id de la partie et de l'id du joueur
	 * 
	 * @param idPartie
	 * @param idJoueur
	 * @return le plateau
	 */
	Plateau rechercherPlateau(int idPartie, int idJoueur);

	/**
	 * Renvoie la liste des cases pour un plateau
	 * 
	 * @param plateau
	 * @return la liste des cases pour un plateau
	 */
	List<Case> listerCases(Plateau plateau);

	/**
	 * Renvoie la liste des jetons en main d'un joueur via son plateau
	 * 
	 * @param plateau
	 * @return la liste des jetons en main
	 */
	public List<Jeton> listerJetonsEnMain(Plateau plateau);

	/**
	 * Renvoie la liste des cases libres d'un plateau de joueur en fonction du
	 * numéro de dé
	 * 
	 * @param plateau
	 * @param de
	 * @return la liste des cases libres
	 */
	List<Case> listerCasesLibres(Plateau plateau, int de);
	
	/**
	 * Renvoi la case qui contient le jeton en question
	 * @param plateau , plateau sur lequel on recherche la case
	 * @param numeroJeton, le numero du jeton que l'on cherche
	 * @return la case contenant le jeton
	 */
	Case recupererLaCaseContentantLeJeton(Plateau plateau, int numeroJeton);
}
