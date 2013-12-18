package be.ipl.finito.daoimpl;

import javax.ejb.Stateless;

import be.ipl.finito.dao.JoueurDao;
import be.ipl.finito.domaine.Joueur;

/**
 * Implémentation du Dao de Joueur
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Stateless
public class JoueurDaoImpl extends DaoImpl<Integer, Joueur> implements
		JoueurDao {

	@Override
	public Joueur chargerPlateaux(Joueur joueur) {
		joueur = rechercher(joueur.getId());
		joueur.getPlateaux().size();
		return joueur;
	}

	@Override
	public Joueur rechercherJoueurViaPseudo(String pseudo) {
		String query = "SELECT j FROM Joueur j WHERE j.login = ?1";
		return recherche(query, pseudo);
	}


}
