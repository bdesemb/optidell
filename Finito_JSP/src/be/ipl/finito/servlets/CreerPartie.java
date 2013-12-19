package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		synchronized (session) {
			Joueur joueur = (Joueur) session.getAttribute("joueur");
			Partie partie = gestionPartie.creerPartie(joueur);
			session.setAttribute("partie", partie);
			ServletContext ctx = getServletContext();
			synchronized (ctx) {
				List<Partie> liste = (List<Partie>) ctx.getAttribute("partiesEnAttente");
				if(liste == null){
					liste = new ArrayList<Partie>();
					ctx.setAttribute("partiesEnAttente", liste);
				}
				liste.add(partie);
			}
		}
		request.setAttribute("title-html", "Partie");
		getServletContext().getNamedDispatcher("attente.html").forward(request, response);
	}

}
