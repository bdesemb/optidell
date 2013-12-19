package be.ipl.finito.servlets;

import java.io.IOException;

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
			Partie partie = gestionPartie.recupererPartieAvecID(Integer.parseInt(request.getParameter("radio_partie")));
			Joueur joueur = null;
			synchronized (session) {
				joueur = gestionJoueur.rechercheJoueurViaPseudo((String) session.getAttribute("pseudo"));
			}
			if (!request.getParameter("etat").equals("suspendue")) {
				if (gestionPartie.ajouterJoueur(partie, joueur)) {
					synchronized (session) {
						session.setAttribute("id_partie", partie.getId());
						int nbrJoueur = gestionPartie.nbrJoueurConnectes(partie);
						if(nbrJoueur == Util.MAX_JOUEURS)
							gestionPartie.debuterPartie(partie);
					}
					getServletContext().getNamedDispatcher("jouerPartie").forward(request, response);
				}

			} else {
				gestionPartie.reprendreJoueur(partie, joueur);
				synchronized (session) {
					session.setAttribute("id_partie", partie.getId());
				}
				
				request.setAttribute("title-html", "Partie");
				getServletContext().getNamedDispatcher("jouerPartie").forward(request, response);
			}
		}else{
			request.setAttribute("title-html", "Lobby");
			getServletContext().getNamedDispatcher("lobby.html").forward(request, response);
		}
	}

}
