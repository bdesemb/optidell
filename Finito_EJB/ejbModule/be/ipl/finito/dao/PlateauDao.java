package be.ipl.finito.dao;

import javax.ejb.Local;

import be.ipl.finito.domaine.Plateau;

/**
 * Interface Dao de Plateau
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 * 
 */
@Local
public interface PlateauDao extends Dao<Integer, Plateau> {
	/**
	 * Charge les cases pour un plateau
	 * 
	 * @param plateau
	 * @return plateau
	 */
	public Plateau chargerCases(Plateau plateau);
	/**
	 * récupère le plateau d'un joueur dans une partie
	 * @param id_partie
	 * @param id_joueur
	 * @return le plateau d'un joueur dans une partie
	 */
	public Plateau recherchePlateauPourJoueurEtPartie(int id_partie, int id_joueur);

}
