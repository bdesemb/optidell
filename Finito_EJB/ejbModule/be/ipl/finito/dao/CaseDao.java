package be.ipl.finito.dao;

import java.util.List;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Plateau;

import javax.ejb.Local;

/**
 * Interface Dao de Case
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 * 
 */
@Local
public interface CaseDao extends Dao<Integer, Case> {

	List<Case> listerCases(Plateau plateau);

}
