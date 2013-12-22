package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.modeles.JoueurFinDePartie;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

/**
 * 
 * @author Marjorie
 * 
 */
@WebServlet(name = "terminer_partie.html")
public class AfficherScores extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	GestionPartie gestionPartie;

	@EJB
	GestionPlateau gestionPlateau;

	@EJB
	GestionJoueur gestionJoueur;

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// Les variables nécessaires
		HttpSession session = request.getSession();
		int idPartie = (Integer) session.getAttribute("id_partie");
		List<Plateau> plateauxDeLaPartie = gestionPartie.listerPlateauxEnJeu(gestionPartie.rechercherPartie(idPartie));
		Vector<JoueurFinDePartie> joueursDeLaPartie = new Vector<JoueurFinDePartie>(3);
		// On vide déjà un peu la session des attributs plus nécessaires (en rapport avec l'affichage du plateau du joueur).
		session.removeAttribute("cases");
		session.removeAttribute("jetonsEnMain");
		session.removeAttribute("suspendu");
		session.removeAttribute("pasEncoreJoue");
		session.removeAttribute("phase2");
		// On fait le traitement nécessaire pour afficher chaque plateau
		for (Plateau p : plateauxDeLaPartie) {
			JoueurFinDePartie joueur = new JoueurFinDePartie(p.getJoueur(), gestionPlateau.calculerScore(p));
			List<Case> casesDuJoueur = gestionPlateau.listerCases(p);
			for (Case c : casesDuJoueur) {
				int cpt = 0;
				if (c.getJeton() != null) {
					joueur.setJeton(cpt / 6, cpt % 6, c.getJeton().getNumero());
				}
				joueur.setCase(cpt / 6, cpt % 6, c.getNumero());
				cpt++;
			}
			joueursDeLaPartie.add(joueur);
		}
		synchronized (session) {
			session.setAttribute("listeJoueurs", joueursDeLaPartie);
			Partie partie = gestionPartie.rechercherPartie(idPartie);
			List<Plateau> plateaux = gestionPartie.listerPlateauxEnJeu(partie);
			Map<Integer, List<Case>> mapPlateaux_idCases = new HashMap<Integer, List<Case>>();
			Map<Integer, Integer> mapPlateaux_idScore = new HashMap<Integer, Integer>();
			for (Plateau p : plateaux) {
				List<Case> cases = gestionPlateau.listerCases(p);
				mapPlateaux_idCases.put(p.getId(), cases);
				mapPlateaux_idScore.put(p.getId(), gestionPlateau.calculerScore(p));
			}
			session.setAttribute("plateaux", plateaux);
			session.setAttribute("mapPlateaux_idCases", mapPlateaux_idCases);
			session.setAttribute("mapPlateaux_idScore", mapPlateaux_idScore);
		}
		getServletContext().getNamedDispatcher("scores.html").forward(request, response);
	}

}
