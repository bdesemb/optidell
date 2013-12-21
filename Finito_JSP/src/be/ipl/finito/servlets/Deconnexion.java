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
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

/**
 * Servlet implementation class Deconnexion
 */
@WebServlet(name="deconnexion.html")
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	GestionPartie gestionPartie;
	
	@EJB
	GestionPlateau gestionPlateau;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Gérer le fait que le joueur peut être dans une partie
		HttpSession session = request.getSession();
		if(session.getAttribute("id_partie") != null) {
			int idPartie = (Integer)session.getAttribute("id_partie");
			int idJoueur = ((Joueur)session.getAttribute("joueur")).getId();
			Partie partie = gestionPartie.rechercherPartie(idPartie);
			gestionPartie.suspendreJoueur(partie, gestionPlateau.rechercherPlateau(idPartie, idJoueur));
		}
		session.invalidate();
		getServletContext().getNamedDispatcher("index.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
