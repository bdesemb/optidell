package be.ipl.finito.uccimpl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import be.ipl.finito.dao.PartieDao;
import be.ipl.finito.dao.PlateauDao;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
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
	GestionPlateau gestionPlateau;

	@Override
	public Partie creerPartie(Joueur joueur) {

		Partie partie = new Partie();
		partieDao.enregistrer(partie);
		Plateau plateau = gestionPlateau.creerPlateau(joueur);
		partieDao.chargerPlateaux(partie);

		List<Plateau> listePlateau = partie.getPlateauEnJeu();
		listePlateau.add(plateau);

		partieDao.mettreAJour(partie);

		return partie;
	}

	@Override
	public Boolean ajouterJoueur(Partie partie, Joueur joueur) {

		if (partie.isEnAttente()) {
			Plateau plateau = gestionPlateau.creerPlateau(joueur);

			partie = partieDao.chargerPlateaux(partie);
			List<Plateau> listePlateau = partie.getPlateauEnJeu();
			listePlateau.add(plateau);

			partieDao.mettreAJour(partie);
			return true;
		}
		return false;
	}

	@Override
	public Jeton piocherJeton(Partie partie) {

		partie = partieDao.chargerJetons(partie);
		partie = partieDao.chargerPlateaux(partie);
		Jeton jeton = partie.piocherJeton();
		partieDao.mettreAJour(partie);
		return jeton;
	}

	public int lancerDe(Partie partie) {
		if (!partie.isEnCours())
			return -1;
		int resultat = partie.lancerDe();
		partie.incrementerTirage();
		partieDao.mettreAJour(partie);
		return resultat;
	}

	public void suspendreJoueur(Partie partie, Plateau plateau) {
		partie.getEtat().suspendrePartie(partie);
		plateau.setSuspendu(true);
		plateauDao.mettreAJour(plateau);
	}

	public int[] finirPartie(Partie partie) {
		partie = partieDao.chargerPlateaux(partie);
		int[] score = partie.finirPartie();
		partieDao.mettreAJour(partie);
		return score;
	}

	@Override
	public void debuterPartie(Partie partie) {
		partie.getEtat().debuterPartie(partie);

	}

	@Override
	public List<Partie> listerPartiesEnAttente() {
		return partieDao.listePartiesEnAttente();
	}

	@Override
	public List<Partie> listerPartiesEnSuspend(Joueur joueur) {
		return partieDao.listePartiesSuspendues(joueur);
	}

	@Override
	public void reprendreJoueur(Partie partie, Plateau plateau) {
		partie = partieDao.chargerPlateaux(partie);
		partie.reprendreJoueur(plateau);
		plateauDao.mettreAJour(plateau);
		partieDao.mettreAJour(partie);
	}

}
