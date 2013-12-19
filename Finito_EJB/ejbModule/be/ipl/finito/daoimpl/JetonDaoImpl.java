package be.ipl.finito.daoimpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import be.ipl.finito.dao.JetonDao;
import be.ipl.finito.domaine.Jeton;

/**
 * Impl�mentation du Dao de Jeton
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Stateless
public class JetonDaoImpl extends DaoImpl<Integer, Jeton> implements JetonDao {

	@Override
	public Jeton r�cupererJeton(int num�ro) {
		String queryString = "SELECT jeton FROM Jeton jeton WHERE jeton.numero = ?1";
		Jeton jeton = super.recherche(queryString, num�ro);
		if (jeton == null) {
			super.enregistrer(new Jeton(num�ro));
		}
		return jeton;
	}

	@Override
	public List<Jeton> lister() {
		List<Jeton> liste= new ArrayList<Jeton>(12);
		for (int i = 1; i <= 12; i++) {
			liste.add(r�cupererJeton(i));
		}
		return liste;
	}
}
