package be.ipl.finito.uccimpl;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.finito.dao.CaseDao;
import be.ipl.finito.dao.PlateauDao;
import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.ucc.GestionPlateau;

@Stateless
public class GestionPlateauImpl implements GestionPlateau{
	
	@EJB
	PlateauDao plateauDao;
	@EJB
	CaseDao caseDao;
	
	
	public Plateau creerPlateau(Joueur joueur) {
		Plateau plateau = new Plateau(joueur);
		plateauDao.enregistrer(plateau);
		plateauDao.chargerCases(plateau);
		for(int i=1;i<=20;i++){
			if(i>=3 && i<=18)
				plateau.getCases().add(new Case(i));
			plateau.getCases().add(new Case(i));
		}
		plateauDao.mettreAJour(plateau);
		return plateau;
	}

	public boolean placerJeton(Plateau plateau, Jeton jeton, Case caseCible) {
		if(caseCible.getJeton()==null){
			caseCible.setJeton(jeton);
			plateau.getJetonsEnMain().remove(jeton);
			caseDao.mettreAJour(caseCible);
			plateauDao.mettreAJour(plateau);
			return true;
		}
	
		return false;
	}

	public boolean deplacerJeton(Plateau plateau, Case caseDepart, Case caseCible) {
		if(caseCible.getJeton()==null){
			Jeton jeton = caseDepart.getJeton();
			caseDepart.setJeton(null);
			caseCible.setJeton(jeton);
			caseDao.mettreAJour(caseCible);
			caseDao.mettreAJour(caseDepart);
			return true;
		}
		return false;
	}

	
}
