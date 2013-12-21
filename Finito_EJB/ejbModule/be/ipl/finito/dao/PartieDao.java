package be.ipl.finito.dao;

import java.util.List;

import javax.ejb.Local;

import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;

/**
 * Interface Dao de Partie
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 * 
 */
@Local
public interface PartieDao extends Dao<Integer, Partie> {

	/**
	 * Renvoie la liste des parties en attente
	 * 
	 * @return la liste des parties en attente
	 */
	List<Partie> listePartiesEnAttente();

	/**
	 * Renvoie la liste des parties suspendues (partie dont au moins un joueur est parti)
	 * 
	 * @param joueur
	 * @return la liste des parties suspendues
	 */
	List<Partie> listePartiesSuspendues(Joueur joueur);

	/**
	 * Charge les plateaux pour une partie
	 * 
	 * @param partie
	 * @return la partie
	 */
	Partie chargerPlateaux(Partie partie);

	/**
	 * Charge les jetons pour une partie
	 * 
	 * @param partie
	 * @return la partie
	 */
	Partie chargerJetons(Partie partie);
}
