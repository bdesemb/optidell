package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

/**
 * Servlet implementation class joueurPartie
 */
@WebServlet(name = "jouerPartie")
public class JouerPartie extends HttpServlet {
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
	public JouerPartie() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("etat").equals("en_attente")) {
			HttpSession session = request.getSession();
			Joueur joueur = (Joueur) session.getAttribute("joueur");
			int idPartie = (Integer) session.getAttribute("id_partie");
			Plateau plateau = gestionPlateau.recherchePlateauPourJoueurEtPartie(idPartie, joueur.getId());
			System.out.println(plateau);
			List<Case> cases = gestionPlateau.recuperLaListeDeCase(plateau);
			List<Jeton> jetonsEnMain = plateau.getJetonsEnMain();
			
			session.setAttribute("cases", cases);
			session.setAttribute("jetonsEnMain", jetonsEnMain);
			
			request.setAttribute("title-html", "Partie");
			
			getServletContext().getNamedDispatcher("pagePlateau.html").forward(
					request, response);
		} else {
			request.setAttribute("title-html", "Lobby");
			getServletContext().getNamedDispatcher("lobby.html").forward(
					request, response);
		}

	}
}
