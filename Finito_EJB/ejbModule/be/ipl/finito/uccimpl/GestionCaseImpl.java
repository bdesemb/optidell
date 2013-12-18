package be.ipl.finito.uccimpl;

import be.ipl.finito.dao.CaseDao;
import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.ucc.GestionCase;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class GestionCaseImpl implements GestionCase{
    @EJB 
    private CaseDao caseDao;
    
    @Override
    public Case creerCase(int numero) {
	Case nvCase = new Case(numero);
	caseDao.enregistrer(nvCase);
	return nvCase;
    }

    @Override
    public boolean placerJeton(Case caseLue, Jeton jeton) {
	if(caseLue.getJeton() != null)
	    return false;
	caseLue.setJeton(jeton);
	caseDao.mettreAJour(caseLue);
	return true;
    }

    @Override
    public boolean retirerJeton(Case caseLue,Jeton jeton) {
	if(caseLue.getJeton() == null)
	    return false;
	caseLue.setJeton(null);
	caseDao.mettreAJour(caseLue);
	return true;
    }

}
