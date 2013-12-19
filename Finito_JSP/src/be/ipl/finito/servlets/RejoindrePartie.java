package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Partie;
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("etat") != null) {
			HttpSession session = request.getSession();
			final Partie partie = gestionPartie.recupererPartieAvecID(Integer.parseInt(request.getParameter("radio_partie")));
			Joueur joueur = (Joueur) session.getAttribute("joueur");
			if (!request.getParameter("etat").equals("suspendue")) {
				if (gestionPartie.ajouterJoueur(partie, joueur)) {
					synchronized (session) {
						session.setAttribute("partie", partie);
						int nbrJoueur = gestionPartie.nbrJoueurConnectes(partie);
						if(nbrJoueur == Util.MAX_JOUEURS) {
							gestionPartie.debuterPartie(partie);
						}
					}
					Timer timer = new Timer();
					TimerTask timerTask = new TimerTask() {
						public void run() {
							System.out.println("WESH COUSIN, JE RUN "+partie.getPlateauEnJeu().size());
							if(partie.getPlateauEnJeu().size()>=2){
								System.out.println("WESH COUSIN, JE LANCE LA PARTIE");
								gestionPartie.debuterPartie(partie);
								List<Partie> liste = ((List<Partie>)getServletContext().getAttribute("partiesEnAttente"));
								liste.remove(partie);
							} else {
								// A faire : gestionPartie.annulerPartie();
							}
						}
					};
					timer.schedule(timerTask, 15000);
					getServletContext().getNamedDispatcher("attente.html").forward(request, response);
				}

			} else {
				gestionPartie.reprendreJoueur(partie, joueur);
				synchronized (session) {
					session.setAttribute("id_partie", partie.getId());
				}
				
				request.setAttribute("title-html", "Partie");
				getServletContext().getNamedDispatcher("attente.html").forward(request, response);
			}
		}else{
			request.setAttribute("title-html", "Lobby");
			getServletContext().getNamedDispatcher("lobby.html").forward(request, response);
		}
	}

}
