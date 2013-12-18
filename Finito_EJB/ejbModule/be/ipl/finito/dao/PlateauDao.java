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
	 * Charger les cases pour un plateau
	 * 
	 * @param plateau
	 * @return plateau
	 */
	public Plateau chargerCases(Plateau plateau);
	
	public Plateau recherchePlateauPourJoueurEtPartie(int id_partie, int id_joueur);

}
