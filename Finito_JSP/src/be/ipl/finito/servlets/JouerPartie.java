package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
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
import be.ipl.finito.ucc.GestionJeton;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;

/**
 * Servlet implementation class joueurPartie
 */
@WebServlet(name = "jouer.html")
public class JouerPartie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	private GestionPartie gestionPartie;

	@EJB
	private GestionJoueur gestionJoueur;

	@EJB
	private GestionJeton gestionJeton;
	
	@EJB
	private GestionPlateau gestionPlateau;

	@EJB
	private GestionCase gestionCase;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JouerPartie() {
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
		ServletContext context = getServletContext();
		HashMap<Integer, HashSet<Integer>> mapPartiesJoueurs;
		synchronized (context) {
			mapPartiesJoueurs = (HashMap<Integer, HashSet<Integer>>) context.getAttribute("mapPartiesJoueurs");
			if(mapPartiesJoueurs == null) {
				mapPartiesJoueurs = new HashMap<Integer, HashSet<Integer>>();
			}
			context.setAttribute("mapPartiesJoueurs", mapPartiesJoueurs);
		}		
		
		HttpSession session = request.getSession();
		Joueur joueur = (Joueur) session.getAttribute("joueur");
		int idPartie = (Integer) session.getAttribute("partie");
		Partie partie = gestionPartie.recupererPartieAvecID(idPartie);
		Plateau plateau = gestionPlateau.recherchePlateauPourJoueurEtPartie(idPartie, joueur.getId());
		
		if(request.getParameter("numeroJeton")!=null){
			int numeroJeton = Integer.parseInt(request.getParameter("numeroJeton"));
			int idCase = Integer.parseInt(request.getParameter("idCase"));
			if(mapPartiesJoueurs.get(idPartie)==null) {
				mapPartiesJoueurs.put(idPartie, new HashSet<Integer>());
			}
			mapPartiesJoueurs.get(idPartie).add(joueur.getId());
			gestionPlateau.placerJeton(plateau, gestionJeton.rechercheJetonPourNumero(numeroJeton), gestionCase.rechercherCasePourId(idCase));
			System.out.println("taille "+mapPartiesJoueurs.get(idPartie).size()+ " "
			+partie.getPlateauEnJeu().size());
			for(Plateau p : partie.getPlateauEnJeu()){
				if(p!=null) {
					System.out.println(p.getJoueur().getId()+" "+p.getPartie().getId());
				}
			}
			if(mapPartiesJoueurs.get(idPartie).size()==partie.getPlateauEnJeu().size()){
				System.out.println("Passe un tour");
				gestionPartie.lancerDe(partie);
				partie = gestionPartie.recupererPartieAvecID(idPartie);
				gestionPartie.piocherJeton(partie);
				partie = gestionPartie.recupererPartieAvecID(idPartie);
				mapPartiesJoueurs.get(idPartie).clear();
			}
		}
		
		plateau = gestionPlateau.recherchePlateauPourJoueurEtPartie(idPartie, joueur.getId());
		List<Case> cases = gestionPlateau.recuperLaListeDeCase(plateau);
		List<Jeton> jetonsEnMain = gestionPlateau.recupererMainPlateau(plateau);

		getServletContext().setAttribute("de", partie.getResultatDe());
		session.setAttribute("cases", cases);
		session.setAttribute("jetonsEnMain", jetonsEnMain);

		request.setAttribute("title-html", "Partie");

		getServletContext().getNamedDispatcher("plateau.html").forward(request, response);

	}
}
