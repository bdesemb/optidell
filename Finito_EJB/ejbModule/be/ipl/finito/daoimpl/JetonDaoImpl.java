package be.ipl.finito.daoimpl;

import java.util.List;

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


	@Override
	public List<Jeton> lister() {
		String queryString = "SELECT jeton FROM Jeton jeton";
		List<Jeton> liste = super.liste(queryString);
		if(liste.isEmpty()) {
			for (int i = 0 ; i<12;i++) {
				super.enregistrer(new Jeton(i+1));
			}
			return super.liste(queryString);
		}
		return liste;
	}
}
