package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.HashSet;

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
	
	
	public Connexion(){
		super();
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		if(getServletContext().getAttribute("partiesEnAttente")==null){
			HashSet<Partie> partiesEventuellementAjouteesParDesTests = new HashSet(gestionPartie.listerPartiesEnAttente());
			getServletContext().setAttribute("partiesEnAttente", partiesEventuellementAjouteesParDesTests);
		}
		
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
		request.setAttribute("title-html", "Lobby");
		session.getServletContext().getNamedDispatcher("lobby.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getNamedDispatcher("index.html").forward(request, response);
	}

}
