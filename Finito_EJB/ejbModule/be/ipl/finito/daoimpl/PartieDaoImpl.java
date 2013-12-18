package be.ipl.finito.daoimpl;

import java.util.List;

import javax.ejb.Stateless;

import be.ipl.finito.dao.PartieDao;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;

/**
 * Implémentation du Dao de Partie
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Stateless
public class PartieDaoImpl extends DaoImpl<Integer, Partie> implements
		PartieDao {


	public List<Partie> listePartiesEnAttente(){
		String query = "SELECT p FROM Partie p " +
				"WHERE p.etat LIKE 'EN_ATTENTE'";
		return liste(query);
	}
	
	public List<Partie> listePartiesSuspendues(Joueur joueur){
		String query = "SELECT pa FROM Partie pa, Plateau pl " +
				"WHERE pa.etat = 'SUSPENDUES' " +
				"pa.id = pl.partie_id "+
				"AND p1.joueur_id = ?1";
		return liste(query, joueur.getId());
	}

	public Partie chargerPlateaux(Partie partie){
		partie = rechercher(partie.getId());
		partie.getPlateauEnJeu().size();
		return partie;
	}
	
	public Partie chargerJetons(Partie partie){
		partie = rechercher(partie.getId());
		partie.getJetonsRestants().size();
		return partie;
	}
}
