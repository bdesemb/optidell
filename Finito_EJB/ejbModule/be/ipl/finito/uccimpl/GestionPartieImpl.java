package be.ipl.finito.uccimpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.finito.dao.JetonDao;
import be.ipl.finito.dao.PartieDao;
import be.ipl.finito.dao.PlateauDao;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.domaine.Partie.Etat;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

@Stateless
public class GestionPartieImpl implements GestionPartie {
	@EJB
	PartieDao partieDao;

	@EJB
	PlateauDao plateauDao;

	@EJB
	JetonDao jetonDao;

	@EJB
	GestionPlateau gestionPlateau;

	@Override
	public Partie creerPartie(final Joueur joueur) {

		Partie partie = new Partie();
		partie = partieDao.enregistrer(partie);
		Plateau plateau = gestionPlateau.creerPlateau(joueur, partie);
		partie = partieDao.chargerPlateaux(partie);

		List<Plateau> listePlateau = partie.getPlateauEnJeu();
		listePlateau.add(plateau);

		partie = partieDao.mettreAJour(partie);
		return partie;
	}

	@Override
	public Partie ajouterJoueur(Partie partie, final Joueur joueur) {
		partie = partieDao.chargerPlateaux(partie);
		Plateau plateau = gestionPlateau.creerPlateau(joueur, partie);
		partie = partieDao.chargerPlateaux(partie);
		
		List<Plateau> listePlateau = partie.getPlateauEnJeu();
		listePlateau.add(plateau);
		
		partie = partieDao.mettreAJour(partie);
		return partie;
	}

	@Override
	public Jeton piocherJeton(Partie partie) {

		partie = partieDao.chargerJetons(partie);
		partie = partieDao.chargerPlateaux(partie);
		Jeton jeton = partie.piocherJeton();
		partie.incrementerTirage();
		partieDao.mettreAJour(partie);
		return jeton;
	}

	public int lancerDe(final Partie partie) {
		if (!partie.isEnCours()) {
			return -1;
		}
		int resultat = partie.lancerDe();
		partieDao.mettreAJour(partie);
		return resultat;
	}

	public void suspendreJoueur(final Partie partie, final Plateau plateau) {
		partie.getEtat().suspendrePartie(partie);
		plateau.setSuspendu(true);
		
		plateauDao.mettreAJour(plateau);
		partieDao.mettreAJour(partie);
	}

	public Partie finirPartie(Partie partie) {
		partie.finirPartie();
		partie = partieDao.mettreAJour(partie);
		return partie;
	}

	@Override
	public Partie debuterPartie(Partie partie) {
		List<Jeton> jetons = jetonDao.lister();
		Collections.shuffle(jetons);
		Map<Integer, Jeton> jetonsRestants = partie.getJetonsRestants();
		for (int i = 0; i < 12; i++) {
			jetonsRestants.put(i, jetons.get(i));
		}
		partie.setJetonsRestants(jetonsRestants);
		partie.debuterPartie();
		partie = partieDao.mettreAJour(partie);
		return partie;
	}

	@Override
	public List<Partie> listerPartiesEnAttente() {
		return partieDao.listePartiesEnAttente();
	}

	@Override
	public List<Partie> listerPartiesEnSuspend(final Joueur joueur) {
		return partieDao.listePartiesSuspendues(joueur);
	}

	@Override
	public Partie reprendreJoueur(Partie partie, final Joueur joueur) {
		partie = partieDao.chargerPlateaux(partie);
		Plateau plateau = plateauDao.recherchePlateauPourJoueurEtPartie(partie.getId(), joueur.getId());
		partie.reprendreJoueur(plateau);
		plateauDao.mettreAJour(plateau);
		partie = partieDao.mettreAJour(partie);
		return partie;
	}

	@Override
	public Partie rechercherPartie(final int id) {
		return partieDao.rechercher(id);
	}

	public List<Plateau> listerPlateauxEnJeu(Partie partie){
		partie = partieDao.chargerPlateaux(partie);
		return partie.getPlateauEnJeu();
	}
	
	public int rechercherNombreJoueursConnectes(final Partie partieEnCours){
		List<Plateau> plateauxEnJeu = listerPlateauxEnJeu(partieEnCours);
		int nbJoueursConnectes = plateauxEnJeu.size();
		for(Plateau p : plateauxEnJeu){
			if(p.isSuspendu()){
				nbJoueursConnectes--;
			}
		}
		return nbJoueursConnectes;
	}

	public Partie annulerPartie(Partie partie) {
		partie.setEtat(Etat.FINI);
		partie = partieDao.mettreAJour(partie);
		return partie;
	}
}
