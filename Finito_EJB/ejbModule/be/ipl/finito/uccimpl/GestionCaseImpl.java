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
    public boolean placerJeton(final Case caseLue, final Jeton jeton) {
	if(caseLue.getJeton() != null) {
		return false;
	}
	caseLue.setJeton(jeton);
	caseDao.mettreAJour(caseLue);
	return true;
    }

    @Override
    public boolean retirerJeton(final Case caseLue,final Jeton jeton) {
	if(caseLue.getJeton() == null) {
		return false;
	}
	caseLue.setJeton(null);
	caseDao.mettreAJour(caseLue);
	return true;
    }

	public Case rechercherCasePourId(final int id) {
		return caseDao.rechercher(id);
	}

}
