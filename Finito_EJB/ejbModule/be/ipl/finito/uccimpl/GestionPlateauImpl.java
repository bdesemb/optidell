package be.ipl.finito.uccimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.finito.dao.CaseDao;
import be.ipl.finito.dao.JetonDao;
import be.ipl.finito.dao.PlateauDao;
import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.ucc.GestionPlateau;

@Stateless
public class GestionPlateauImpl implements GestionPlateau{
	
	@EJB
	PlateauDao plateauDao;
	@EJB
	CaseDao caseDao;
	@EJB
	JetonDao jetonDao;
	
	
	public Plateau creerPlateau(Joueur joueur, Partie partie) {
		Plateau plateau = new Plateau(joueur,partie);
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
			//plateau.getJetonsEnMain().remove(jeton);
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

	@Override
	public Plateau recherchePlateauPourJoueurEtPartie(int idPartie, int idJoueur) {
		return plateauDao.recherchePlateauPourJoueurEtPartie(idPartie, idJoueur);
	}

	@Override
	public List<Case> recuperLaListeDeCase(Plateau plateau) {
		plateau = plateauDao.chargerCases(plateau);
		return plateau.getCases();
	}

	public List<Jeton> recupererMainPlateau(Plateau plateau){
		List<Jeton> jetons = jetonDao.lister();
		List<Case> cases = recuperLaListeDeCase(plateau);
		for(Case c : cases){
			if(c.getJeton()!=null)
				jetons.remove(c.getJeton());
		}
		Map<Integer, Jeton> jetonsRestants = plateau.getPartie().getJetonsRestants();
		for(int i = plateau.getPartie().getIndiceTirage();i<12;i++){
			jetons.remove(jetonsRestants.get(i));
		}
		return jetons;
	}

	@Override
	public List<Case> recupererLesCasesLibres(Plateau plateau, int de) {
		List<Case> casesLibres = new ArrayList<Case>();
		List<Case> toutesLesCases = recuperLaListeDeCase(plateau);
		int indice = -1;
		for(int i=0;i<toutesLesCases.size();i++){
			if(toutesLesCases.get(i).getNumero()==de){
				indice=i;
				break;
			}
		}
		
		if(toutesLesCases.get(indice).getJeton()==null){
			casesLibres.add(toutesLesCases.get(indice));
			return casesLibres;
		}
		
		if(de!=20 && toutesLesCases.get(indice+1).getNumero()==de && toutesLesCases.get(indice+1).getJeton()==null){
			casesLibres.add(toutesLesCases.get(indice+1));
			return casesLibres;
		}

		for(int i = indice; i>=0; i--){
			Case caseActuelle = toutesLesCases.get(i);
			if(caseActuelle.getJeton() == null){
				casesLibres.add(caseActuelle);
				break;
			}
				
		}
		for(int i = indice; i < toutesLesCases.size(); i++){
			Case caseActuelle = toutesLesCases.get(i);
			if(caseActuelle.getJeton() == null){
				casesLibres.add(caseActuelle);
				break;
			}
		}
		return casesLibres;
	}
	
}
