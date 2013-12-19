package be.ipl.finito.dao;

import java.util.List;

import javax.ejb.Local;
import be.ipl.finito.domaine.Jeton;

/**
 * Interface Dao de Jeton
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit

 */
@Local
public interface JetonDao extends Dao<Integer, Jeton> {
	Jeton r�cupererJeton(int num�ro);
	List<Jeton> lister();
}
