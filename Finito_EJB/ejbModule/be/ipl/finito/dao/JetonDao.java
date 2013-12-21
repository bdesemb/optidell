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
	 * r�cup�re tous les jetons
	 * 
	 * @return la liste de tous les jetons
	 */
	List<Jeton> lister();

	/**
	 * R�cup�re la main du joueur (jetons � la disposition du joueur pour �tre plac� sur le plateau)
	 * 
	 * @param plateau
	 * @return la main du joueur
	 */
	List<Jeton> recupererMainJoueur(Plateau plateau);
}
