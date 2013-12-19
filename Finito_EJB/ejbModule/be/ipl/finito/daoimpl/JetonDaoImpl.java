package be.ipl.finito.daoimpl;

import java.util.List;

import javax.ejb.Stateless;

import be.ipl.finito.dao.JetonDao;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Plateau;

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

	public List<Jeton> recupererMainJoueur(Plateau plateau) {
		String query = "SELECT j FROM Jeton j, Plateau pl, Partie pa WHERE ?1=pa.id " +
				/*"AND j.id NOT IN(SELECT value(pa2.jetonRestant).id FROM Partie pa2 WHERE pa2.id=pa.id " +
				"AND key(pa2.jetonRestant)>pa2.indiceTirage) " +*/
				"AND j.id NOT IN(SELECT j3.id FROM Case c, Jeton j3 WHERE ?2 = c.plateau.id) ";
		return liste(query, plateau.getPartie().getId(), plateau.getId());
		
	}
}
