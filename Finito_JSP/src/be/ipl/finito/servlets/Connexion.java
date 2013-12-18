package be.ipl.finito.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;


/**
 * Servlet implementation class Login
 */
@WebServlet(name="connexion.html")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestionJoueur gestionJoueur;
	
	@EJB
	private GestionPartie gestionPartie;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pseudo = request.getParameter("pseudo");
		String password = request.getParameter("password");
		Joueur joueur = gestionJoueur.connexion(pseudo, password);
		if (joueur == null) {
			String message = "L'association pseudo/mot de passe n'est pas valide";
			request.setAttribute("message", message);
			getServletContext().getNamedDispatcher("index.html").forward(request, response);
			return;
		}
		HttpSession session = request.getSession();		
		synchronized (session) {
			session.setAttribute("joueur", joueur);
			session.setAttribute("partiesSuspendues", gestionPartie.listerPartiesEnSuspend(joueur));
		}
		ServletContext ctx = request.getServletContext();
		synchronized(ctx) {
			ctx.setAttribute("partiesEnAttente", gestionPartie.listerPartiesEnAttente());
		}
		session.getServletContext().getNamedDispatcher("lobby.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getNamedDispatcher("lobby.html").forward(request, response);
	}

}
