package be.ipl.finito.daoimpl;

import javax.ejb.Stateless;

import be.ipl.finito.dao.JetonDao;
import be.ipl.finito.domaine.Jeton;

/**
 * Implémentation du Dao de Jeton
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Stateless
public class JetonDaoImpl extends DaoImpl<Integer, Jeton> implements JetonDao {

}
