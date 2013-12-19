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
	List<Jeton> lister();
	
	List<Jeton> recupererMainJoueur(Plateau plateau);
}
