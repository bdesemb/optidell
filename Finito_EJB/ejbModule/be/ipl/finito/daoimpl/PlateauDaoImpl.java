package be.ipl.finito.daoimpl;

import javax.ejb.Stateless;

import be.ipl.finito.dao.PlateauDao;
import be.ipl.finito.domaine.Plateau;

/**
 * Implémentation du Dao de Plateau
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Stateless
public class PlateauDaoImpl extends DaoImpl<Integer, Plateau> implements PlateauDao {

	public Plateau chargerCases(Plateau plateau) {
		plateau = rechercher(plateau.getId());
		// Permet de charger
		plateau.getCases().size();
		return plateau;
	}

}
