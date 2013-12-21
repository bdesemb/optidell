package be.ipl.finito.dao;

import java.util.List;

import javax.ejb.Local;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Plateau;

/**
 * Interface Dao de Jeton
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@Local
public interface JetonDao extends Dao<Integer, Jeton> {

	/**
	 * récupère tous les jetons
	 * 
	 * @return la liste de tous les jetons
	 */
	List<Jeton> lister();

	/**
	 * Récupère la main du joueur (jetons à la disposition du joueur pour être placé sur le plateau)
	 * 
	 * @param plateau
	 * @return la main du joueur
	 */
	List<Jeton> recupererMainJoueur(Plateau plateau);
}
