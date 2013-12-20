package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
import be.ipl.finito.modeles.DonneesDUnePartie;
import be.ipl.finito.ucc.GestionCase;
import be.ipl.finito.ucc.GestionJeton;
import be.ipl.finito.ucc.GestionJoueur;
import be.ipl.finito.ucc.GestionPartie;
import be.ipl.finito.ucc.GestionPlateau;
import be.ipl.finito.util.Util;

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
		
		final HashMap<Integer, DonneesDUnePartie> donneesDesParties =  (HashMap<Integer, DonneesDUnePartie>) context.getAttribute("donneesDesParties");
		
		HttpSession session = request.getSession();
		Joueur joueur = (Joueur) session.getAttribute("joueur");
		final int idPartie = (Integer) session.getAttribute("partie");
		Partie partie = gestionPartie.recupererPartieAvecID(idPartie);
		Plateau plateau = gestionPlateau.recherchePlateauPourJoueurEtPartie(idPartie, joueur.getId());
		
		Timer timer = new Timer();
		TimerTask timertask = new TimerTask() {
			
			@Override
			public void run() {
				Partie partie = gestionPartie.recupererPartieAvecID(idPartie);
				for(Plateau p : partie.getPlateauEnJeu()){
					if(!donneesDesParties.get(idPartie).getJoueurs().contains(p.getJoueur().getId())){
						gestionPartie.suspendreJoueur(partie, p);
					}
				}
			}
		};
		
		
		if(request.getParameter("numeroJeton")!=null){
			int numeroJeton = Integer.parseInt(request.getParameter("numeroJeton"));
			int idCase = Integer.parseInt(request.getParameter("idCase"));

			gestionPlateau.placerJeton(plateau, gestionJeton.rechercheJetonPourNumero(numeroJeton), gestionCase.rechercherCasePourId(idCase));
			System.out.println("taille "+donneesDesParties.get(idPartie).getJoueurs().size()+ " "
			+partie.getPlateauEnJeu().size());
			for(Plateau p : partie.getPlateauEnJeu()){
				if(p!=null) {
					System.out.println(p.getJoueur().getId()+" "+p.getPartie().getId());
				}
			}
			if(donneesDesParties.get(idPartie).getJoueurs().size()==partie.getPlateauEnJeu().size()){
				System.out.println("Passe un tour");
				gestionPartie.lancerDe(partie);
				partie = gestionPartie.recupererPartieAvecID(idPartie);
				gestionPartie.piocherJeton(partie);
				partie = gestionPartie.recupererPartieAvecID(idPartie);
				donneesDesParties.get(idPartie).getJoueurs().clear();
				timer.schedule(timertask,Util.TEMPS_INACTIVITE);
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
