package be.ipl.finito.servlets;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.ipl.finito.ucc.GestionJoueur;

/**
 * Servlet implementation class Inscription
 */
@WebServlet(name = "inscription.html")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	private GestionJoueur gestionJoueur;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inscription() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("nom") == null) {
			request.setAttribute("title-html", "Inscription");
			getServletContext().getNamedDispatcher("pageInscription.html").forward(request, response);
		}
		else{
			String nom = request.getParameter("nom");
			String prenom = request.getParameter("prenom");
			String email = request.getParameter("mail");
			String login = request.getParameter("pseudo");
			if(gestionJoueur.rechercheJoueurViaPseudo(login) != null){
				String message = "Le pseudo est déja présent";
				request.setAttribute("message", message);
				getServletContext().getNamedDispatcher("pageInscription.html").forward(request, response);
				return;
			}
			String motDePasse = request.getParameter("password");
			
			gestionJoueur.inscription(nom, prenom, email, login, motDePasse);
			String message = "Inscription reussie, veuillez vous connecter";
			request.setAttribute("message", message);
			getServletContext().getNamedDispatcher("index.html").forward(request, response);
			return;
		}
	}

}
