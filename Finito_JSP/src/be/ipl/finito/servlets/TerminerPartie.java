package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Plateau;
import be.ipl.finito.modeles.JoueurFinDePartie;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

/**
 * 
 * @author Marjorie
 *
 */
@WebServlet(name = "terminer_partie.html")
public class TerminerPartie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	GestionPartie gestionPartie;
	
	@EJB
	GestionPlateau gestionPlateau;
	
	@EJB
	GestionJoueur gestionJoueur;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int idPartie = (Integer)session.getAttribute("id_partie");
		List<Plateau> plateauxDeLaPartie = gestionPartie.listerPlateauxEnJeu(gestionPartie.rechercherPartie(idPartie));
		Vector<JoueurFinDePartie> joueursDeLaPartie = new Vector<JoueurFinDePartie>(3);
		for (Plateau p : plateauxDeLaPartie) {
			JoueurFinDePartie joueur = new JoueurFinDePartie(p.getJoueur(), p.calculerScore());
			List<Case> casesDuJoueur = p.getCases();
			for(Case c : casesDuJoueur) {
				int cpt = 0;
				joueur.setJeton(cpt/6, (cpt++)%6, c.getJeton().getNumero());
				joueur.setCase(cpt/6, (cpt++)%6, c.getNumero());
			}
			joueursDeLaPartie.add(joueur);
		}
		synchronized (session) {
			session.setAttribute("listeJoueurs", joueursDeLaPartie);
		}
		getServletContext().getNamedDispatcher("scores.html").forward(request, response);
	}

}
