package be.ipl.finito.uccimpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.finito.dao.CaseDao;
import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.ucc.GestionCase;

@Stateless
public class GestionCaseImpl implements GestionCase{
    @EJB 
    private CaseDao caseDao;
    
    @Override
    public Case creerCase(final int numero) {
	Case nvCase = new Case(numero);
	caseDao.enregistrer(nvCase);
	return nvCase;
    }

    @Override
    public boolean placerJeton(final Case caseDestination, final Jeton jeton) {
	if(caseDestination.getJeton() != null) {
		return false;
	}
	caseDestination.setJeton(jeton);
	caseDao.mettreAJour(caseDestination);
	return true;
    }

    @Override
    public boolean retirerJeton(final Case caseDestination,final Jeton jeton) {
	if(caseDestination.getJeton() == null) {
		return false;
	}
	caseDestination.setJeton(null);
	caseDao.mettreAJour(caseDestination);
	return true;
    }

	public Case rechercherCase(final int id) {
		return caseDao.rechercher(id);
	}

}
