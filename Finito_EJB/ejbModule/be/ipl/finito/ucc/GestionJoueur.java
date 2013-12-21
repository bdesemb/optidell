package be.ipl.finito.ucc;

import java.util.List;

import be.ipl.finito.domaine.Joueur;
import javax.ejb.Remote;

/**
 * Cas d'utilisation pour la gestion des cases
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@Remote
public interface GestionJoueur {
	/**
	 * Inscription d'un nouveau joueur
	 * 
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @param pseudo
	 * @param motDePasse
	 * @return le joueur inscrit
	 */
	Joueur inscription(String nom, String prenom, String mail, String pseudo, String motDePasse);

	/**
	 * Connexion d'un joueur
	 * 
	 * @param pseudo
	 * @param motDePasse
	 * @return le joueur connecté
	 */
	Joueur connexion(String pseudo, String motDePasse);

	/**
	 * Renvoie la liste de tous les joueurs
	 * 
	 * @return la liste de tous les joueurs
	 */
	List<Joueur> listeJoueur();

	/**
	 * Recheche un joueur via son pseudo
	 * 
	 * @param pseudo
	 * @return le joueur
	 */
	Joueur rechercheJoueurViaPseudo(String pseudo);
}
