package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.finito.modeles.DonneesDUnePartie;

/**
 * Servlet implementation class QuitterPartie
 */
@WebServlet(name = "terminer_partie.html")
public class TerminerParties extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		// Les variables nécessaires
		HttpSession session = request.getSession();
		ServletContext context = getServletContext();
		// On supprime les attributs retenus pour l'affichage du plateau
		synchronized (session) {
			session.removeAttribute("listeJoueurs");
			session.removeAttribute("plateaux");
			session.removeAttribute("mapPlateaux_idCases");
			session.removeAttribute("mapPlateaux_idScore");
			// On supprime de la map la classe modèle retenant les données pour cette partie
			synchronized (context) {
				HashMap<Integer, DonneesDUnePartie> donneesDesParties =  (HashMap<Integer, DonneesDUnePartie>) context.getAttribute("donneesDesParties");
				donneesDesParties.remove(session.getAttribute("id_partie"));
			}
			session.removeAttribute("id_partie");
		}
		// On redirige vers le lobby (la page connexion va rediriger, elle sert pour chercher les parties suspendues également)
		getServletContext().getNamedDispatcher("connexion.html").forward(request, response);
	}

	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
