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
	 * Charger les plateaux pour un joueur
	 * @param joueur
	 * @return le joueur
	 */
	Joueur chargerPlateaux(Joueur joueur);
	
	Joueur rechercherJoueurViaPseudo(String pseudo);
}
