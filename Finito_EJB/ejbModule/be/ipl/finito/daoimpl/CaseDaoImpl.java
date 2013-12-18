package be.ipl.finito.daoimpl;

import java.util.List;

import javax.ejb.Stateless;

import be.ipl.finito.dao.CaseDao;
import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Plateau;

/**
 * Implémentation du Dao de Case
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Stateless
public class CaseDaoImpl extends DaoImpl<Integer, Case> implements CaseDao {
	
	@Override
	public List<Case> listerCases(Plateau plateau) {
		String queryString = "SELECT c FROM Case WHERE PLATEAU_ID = ?1";
		return liste(queryString, plateau.getId());
	}
}
