package be.ipl.finito.dao;

import javax.ejb.Local;

import be.ipl.finito.domaine.Joueur;

/**
 * Interface Dao de Joueur
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 * 
 */
@Local
public interface JoueurDao extends Dao<Integer, Joueur> {

	/**
	 * Charge les plateaux pour un joueur
	 * 
	 * @param joueur
	 * @return le joueur dont les plateaux on été chargés
	 */
	Joueur chargerPlateaux(Joueur joueur);

	/**
	 * Recherche le joueur via son pseudo
	 * 
	 * @param pseudo
	 * @return le joueur via son pseudo
	 */
	Joueur rechercherJoueurViaPseudo(String pseudo);
}
