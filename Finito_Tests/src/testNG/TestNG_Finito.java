package testNG;

import static org.testng.AssertJUnit.assertEquals;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.ucc.GestionCase;
import be.ipl.finito.ucc.GestionJeton;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

public class TestNG_Finito {

	private static GestionPartie gestionParties;
	private static GestionPlateau gestionPlateaux;
	private static GestionJoueur gestionJoueurs;
	private static GestionCase gestionCases;
	private static GestionJeton gestionJeton;

	private static String[] nomsJoueurs = { "Leconte", "Debacker", "Choquet" };
	private static String[] prenomsJoueurs = { "Emmeline", "Michel", "Olivier" };
	private static String[] loginsJoueurs = { "el", "md", "oc" };
	private static String[] motDePasseJoueurs = { "em", "mi", "ol" };
	private static String[] emailJoueurs = { "emmeline.leconte@ipl.be", "michel.debacker@ipl.be",
			"olivier.choquet@ipl.be" };
	private static Joueur[] joueurs = new Joueur[nomsJoueurs.length];

	private static Partie partieEnCours = null;
	private static Jeton seulJetonPlace;

	@BeforeClass
	public void init() throws Exception {
		Context jndi = new InitialContext();
		gestionParties = (GestionPartie) jndi
				.lookup("ejb:Finito_EAR/Finito_EJB/GestionPartieImpl!be.ipl.finito.ucc.GestionPartie");
		gestionPlateaux = (GestionPlateau) jndi
				.lookup("ejb:Finito_EAR/Finito_EJB/GestionPlateauImpl!be.ipl.finito.ucc.GestionPlateau");
		gestionJoueurs = (GestionJoueur) jndi
				.lookup("ejb:Finito_EAR/Finito_EJB/GestionJoueurImpl!be.ipl.finito.ucc.GestionJoueur");
		gestionCases = (GestionCase) jndi
				.lookup("ejb:Finito_EAR/Finito_EJB/GestionCaseImpl!be.ipl.finito.ucc.GestionCase");

	}

	@Test(priority = 1)
	public void testInscriptionJoueurs() {
		for (int i = 0; i < nomsJoueurs.length; i++) {
			joueurs[i] = gestionJoueurs.inscription(nomsJoueurs[i], prenomsJoueurs[i], emailJoueurs[i],
					loginsJoueurs[i], motDePasseJoueurs[i]);
		}
		List<Joueur> listeJoueurs = gestionJoueurs.listerJoueurs();
		for (int i = 0; i < nomsJoueurs.length; i++) {
			assertEquals(i + 1, listeJoueurs.get(i).getId());
			assertEquals(nomsJoueurs[i], listeJoueurs.get(i).getNom());
			assertEquals(prenomsJoueurs[i], listeJoueurs.get(i).getPrenom());
			assertEquals(loginsJoueurs[i], listeJoueurs.get(i).getLogin());
			assertEquals(motDePasseJoueurs[i], listeJoueurs.get(i).getMotDePasse());
			assertEquals(emailJoueurs[i], listeJoueurs.get(i).getEmail());
		}
	}

	@Test(priority = 2)
	public void testConnexionJoueur() {
		for (int i = 0; i < nomsJoueurs.length; i++) {
			assertEquals(gestionJoueurs.connexion(loginsJoueurs[i], motDePasseJoueurs[i]), joueurs[i]);
		}
	}

	@Test(priority = 3)
	public void testCreerPartie() {
		Partie partie = gestionParties.creerPartie(joueurs[0]);
		assertEquals(gestionParties.listerPartiesEnAttente().get(0), partie);
	}

	@Test(priority = 4)
	public void testRejoindrePartie() {
		Partie partie = gestionParties.listerPartiesEnAttente().get(0);
		gestionParties.ajouterJoueur(partie, joueurs[1]);
		partieEnCours = partie;
		List<Plateau> liste = gestionParties.listerPlateauxEnJeu(partieEnCours);
		assertEquals(liste.get(1).getJoueur(), joueurs[1]);
	}

	@Test(priority = 5)
	public void testDebuterPartie() {
		partieEnCours = gestionParties.listerPartiesEnAttente().get(0);
		gestionParties.debuterPartie(partieEnCours);
		partieEnCours = gestionParties.rechercherPartie(partieEnCours.getId());
		assertEquals(true, partieEnCours.isEnCours());
		assertEquals(partieEnCours.getJetonsRestants().size(), 12);
	}

	@Test(priority = 6)
	public void testJouerJetonSurUnPlateau() {
		Plateau p = gestionParties.listerPlateauxEnJeu(partieEnCours).get(1);
		List<Jeton> main = gestionPlateaux.listerJetonsEnMain(gestionParties.listerPlateauxEnJeu(partieEnCours)
				.get(0));
		List<Case> casesDuPlateau = gestionPlateaux.listerCases(p);
		assertEquals(true, gestionPlateaux.placerJeton(p, main.get(0), casesDuPlateau.get(20)));
		assertEquals(main.get(0), gestionPlateaux.listerCases(p).get(20).getJeton());
	}

	@Test(priority = 7)
	public void testNbrPlateaux() {
		List<Plateau> liste = gestionParties.listerPlateauxEnJeu(partieEnCours);
		int nbrPlateaux = liste.size();
		assertEquals(2, nbrPlateaux);
	}

	@Test(priority = 7)
	public void testDeplacerJetonSurUnPlateau() {
		Plateau p = gestionParties.listerPlateauxEnJeu(partieEnCours).get(1);
		Case c = gestionPlateaux.listerCases(p).get(20);
		seulJetonPlace = c.getJeton();
		Case c2 = gestionPlateaux.listerCases(p).get(21);
		assertEquals(true, gestionPlateaux.deplacerJeton(p, c, c2));
		assertEquals(seulJetonPlace, gestionPlateaux.listerCases(p).get(21).getJeton());
		assertEquals(null, gestionPlateaux.listerCases(p).get(20).getJeton());
	}

	@Test(priority = 8)
	public void testSuspendreUnePartie() {
		// On suspend le plateau du joueurs 1 dans la table joueurs
		Plateau p = gestionParties.listerPlateauxEnJeu(partieEnCours).get(1);
		gestionParties.suspendreJoueur(partieEnCours, p);
		partieEnCours = gestionParties.rechercherPartie(partieEnCours.getId());
		assertEquals(Partie.Etat.SUSPENDU, partieEnCours.getEtat());
		assertEquals(true, gestionParties.listerPlateauxEnJeu(partieEnCours).get(1).isSuspendu());
		assertEquals(1, gestionParties.listerPartiesEnSuspend(joueurs[1]).size());
		assertEquals(1, gestionParties.getNombresJoueursConnectes(partieEnCours));
	}

	@Test(priority = 9)
	public void testReprendreUnJoueur() {
		gestionParties.reprendreJoueur(gestionParties.rechercherPartie(partieEnCours.getId()), joueurs[1]);
		partieEnCours = gestionParties.rechercherPartie(partieEnCours.getId());
		assertEquals(0, gestionParties.listerPartiesEnSuspend(joueurs[1]).size());
		assertEquals(2, gestionParties.getNombresJoueursConnectes(partieEnCours));
		assertEquals(Partie.Etat.EN_COURS, partieEnCours.getEtat());
		assertEquals(false, gestionParties.listerPlateauxEnJeu(partieEnCours).get(1).isSuspendu());
		
		Plateau p = gestionParties.listerPlateauxEnJeu(partieEnCours).get(1);
		assertEquals(seulJetonPlace, gestionPlateaux.listerCases(p).get(21).getJeton());
	}

}
