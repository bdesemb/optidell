package be.ipl.finito.uccimpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.finito.dao.JetonDao;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.ucc.GestionJeton;

@Stateless
public class GestionJetonImpl implements GestionJeton{
    @EJB 
    private JetonDao jetonDao;

	public Jeton rechercherJeton(final int id) {
		return jetonDao.rechercher(id);
	}
    
    
}
