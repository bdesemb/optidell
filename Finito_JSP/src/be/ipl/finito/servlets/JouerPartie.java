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
import be.ipl.finito.domaine.Partie;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.ucc.GestionCase;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

/**
 * Servlet implementation class joueurPartie
 */
@WebServlet("/joueurPartie")
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("etat").equals("encours")){
			HttpSession session =  request.getSession();
			Joueur joueur  = gestionJoueur.rechercheJoueurViaPseudo((String) session.getAttribute("pseudo"));
			List<Plateau> plateaux = joueur.getPlateaux();
			Plateau plateau = null;
			for(Plateau p : plateaux){
				if(!p.isSuspendu()){
					plateau = p;
				}
			}
			List<Case> cases = plateau.getCases();	
			List<Jeton> jetonsEnMain= plateau.getJetonsEnMain();
			
			session.setAttribute("cases", cases);
			session.setAttribute("jetonsEnMain", jetonsEnMain);
			
			getServletContext().getNamedDispatcher("pagePlateau.html").forward(request, response);
		}
		getServletContext().getNamedDispatcher("lobby.html").forward(request, response);
	
	}
}
