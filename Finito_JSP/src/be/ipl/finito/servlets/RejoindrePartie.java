package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.modeles.DonneesDUnePartie;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;
import be.ipl.finito.util.Util;

/**
 * Servlet implementation class RejoindrePartie
 */
@WebServlet(name = "rejoindrePartie.html")
public class RejoindrePartie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;

	@EJB
	private GestionJoueur gestionJoueur;

	@EJB
	private GestionPlateau gestionPlateau;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RejoindrePartie() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		String etat = request.getParameter("etat");
		final ServletContext context = getServletContext();
		final HttpSession session = request.getSession();

		synchronized (context) {
			final HashMap<Integer, DonneesDUnePartie> donneesDesParties = (HashMap<Integer, DonneesDUnePartie>) context
					.getAttribute("donneesDesParties");
			
			int idPartie = Integer.parseInt(request
					.getParameter("radio_partie"));
			Partie partie = gestionPartie.recupererPartieAvecID(idPartie);
			Joueur joueur = (Joueur) session.getAttribute("joueur");
			DonneesDUnePartie donneesDeLaPartie = donneesDesParties.get(idPartie);
			
			if (etat != null && !etat.equals("suspendue")) {
				partie = gestionPartie.ajouterJoueur(partie, joueur);

				session.setAttribute("id_partie", idPartie);
				int nbrJoueurs = gestionPartie.nbrJoueurConnectes(partie);
				donneesDesParties.get(partie.getId()).getJoueursNumTours().put(joueur.getId(), 0);
				if (nbrJoueurs == Util.MAX_JOUEURS) {
					partie = gestionPartie.debuterPartie(partie);
					donneesDeLaPartie.setEtat(partie.getEtat().toString());
					donneesDeLaPartie.setResultatDe(partie.getResultatDe());
					donneesDeLaPartie.getTimer().cancel();
					donneesDeLaPartie.setTimer(null);
				}
				getServletContext().getNamedDispatcher("attente.html").forward(
						request, response);
			} else if (etat != null && etat.equals("suspendue")) {
				gestionPartie.reprendreJoueur(partie, joueur);
				session.setAttribute("id_partie", partie.getId());
				request.setAttribute("title-html", "Partie");
				getServletContext().getNamedDispatcher("attente.html").forward(
						request, response);
			}
		}
	}

}
