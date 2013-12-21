package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.print.DocFlavor;
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
import be.ipl.finito.util.Util;

/**
 * Servlet implementation class CreerPartie
 */
@WebServlet(name = "creerPartie.html")
public class CreerPartie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestionJoueur gestionJoueur;
	@EJB
	private GestionPartie gestionPartie;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreerPartie() {
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
		final ServletContext context = getServletContext();
		final HttpSession session = request.getSession();
		synchronized (context) {
			if (context.getAttribute("donneesDesParties") == null) {
				HashMap<Integer, DonneesDUnePartie> donneesDesParties = new HashMap<Integer, DonneesDUnePartie>();
				context.setAttribute("donneesDesParties", donneesDesParties);
			}

			final HashMap<Integer, DonneesDUnePartie> donneesDesParties = (HashMap<Integer, DonneesDUnePartie>) context
					.getAttribute("donneesDesParties");
			final Joueur joueur = (Joueur) session.getAttribute("joueur");
			Partie partie = gestionPartie.creerPartie(joueur);

			session.setAttribute("partie", partie.getId());
			donneesDesParties.put(partie.getId(),
					new DonneesDUnePartie(partie.getId()));
			donneesDesParties.get(partie.getId()).getJoueursNumTours().put(joueur.getId(), 0);
			List<Partie> liste = (List<Partie>) context
					.getAttribute("partiesEnAttente");
			if (liste == null) {
				liste = new ArrayList<Partie>();
			}
			liste.add(partie);
			context.setAttribute("partiesEnAttente", liste);
			System.out.println("Création d'une partie : "+gestionPartie.listeDesPlateauxEnJeu(partie).size());
			// Création du timer et de son job
			Timer timer = new Timer();
			TimerTask timerTask = new TimerTask() {
				public void run() {
					Partie partie = gestionPartie
							.recupererPartieAvecID((Integer) session
									.getAttribute("partie"));
					if (gestionPartie.listeDesPlateauxEnJeu(partie).size() >= Util.MIN_JOUEURS) {
						partie = gestionPartie.debuterPartie(partie);
						List<Partie> partieEnCours = (List<Partie>) context
								.getAttribute("partiesEnAttente");
						partieEnCours.remove(partie);
						context.setAttribute("partiesEnAttente", partieEnCours);
						donneesDesParties.get(partie.getId()).setEtat(
								partie.getEtat().toString());
						donneesDesParties.get(partie.getId()).setTimer(null);
					} else {
						System.out.println("Dans timer (else)");
						// A faire : gestionPartie.annulerPartie();
					}
				}
			};
			timer.schedule(timerTask, Util.TEMPS_DEBUT_PARTIE*2);
			donneesDesParties.get(partie.getId()).setTimer(timer);
		}
		request.setAttribute("title-html", "Partie");
		getServletContext().getNamedDispatcher("attente.html").forward(request,
				response);
	}

}
