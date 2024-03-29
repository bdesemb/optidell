package be.ipl.finito.ucc;

import java.util.List;

import javax.ejb.Remote;

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
public interface GestionPartie {

	/**
	 * Cr�e une partie avec le joueur l'ayant cr��e
	 * 
	 * @param joueur
	 *            le cr�ateur de la partie
	 * @return la partie cr��e
	 */
	Partie creerPartie(Joueur joueur);

	/**
	 * Ajoute un joueur � une partie
	 * 
	 * @param partie
	 * @param joueur
	 * @return la partie
	 */
	Partie ajouterJoueur(Partie partie, Joueur joueur);

	/**
	 * pioche un nouveau jeton dans une partie
	 * 
	 * @param partie
	 * @return le jeton pioch�
	 */
	Jeton piocherJeton(Partie partie);

	/**
	 * Lance un d�
	 * 
	 * @param partie
	 * @return la valeur du d�
	 */
	int lancerDe(Partie partie);

	/**
	 * Suspend un joueur
	 * 
	 * @param partie
	 * @param plateau
	 */
	void suspendreJoueur(Partie partie, Plateau plateau);

	/**
	 * Finit la partie
	 * 
	 * @param partie
	 * @return la table des scores
	 */
	Partie finirPartie(Partie partie);

	/**
	 * D�bute une partie
	 * 
	 * @param partie
	 * @return la partie
	 */
	Partie debuterPartie(Partie partie);

	/**
	 * Reprend un joueur dans une partie
	 * 
	 * @param partie
	 * @param joueur
	 * @return 
	 */
	Partie reprendreJoueur(Partie partie, Joueur joueur);

	/**
	 * Renvoie la liste des parties en attente
	 * 
	 * @return la liste des parties en attente
	 */
	List<Partie> listerPartiesEnAttente();

	/**
	 * Renvoie la liste des parties en suspend
	 * 
	 * @param joueur
	 * @return la liste des parties en suspend
	 */
	List<Partie> listerPartiesEnSuspend(Joueur joueur);

	/**
	 * Renvoie la partie via son id
	 * 
	 * @param id
	 * @return la partie
	 */
	Partie rechercherPartie(int id);

	/**
	 * Renvoie la liste des plateaux en jeu pour une partie
	 * 
	 * @param partie
	 * @return la liste des plateaux en jeu
	 */
	List<Plateau> listerPlateauxEnJeu(Partie partie);

	/**
	 * Rechercher le nombre de joueurs connectes pour une partie
	 * @param partieEnCours
	 * @return le nombre de joueurs connectes pour une partie
	 */
	int rechercherNombreJoueursConnectes(Partie partieEnCours);

	/**
	 * Annule la partie pass�e en param�tre
	 * @param partieEnCours la partie
	 * @return la partie modifi�e
	 */
	Partie annulerPartie(Partie partie);

}
