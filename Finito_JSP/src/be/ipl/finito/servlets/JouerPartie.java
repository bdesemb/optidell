package be.ipl.finito.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
		
		HttpSession session = request.getSession(false);
		Joueur joueur = (Joueur) session.getAttribute("joueur");
		System.out.println(joueur+" "+(joueur!=null?joueur.getLogin():"")+ " " +session);
		final int idPartie = (Integer) session.getAttribute("id_partie");
		Partie partie = gestionPartie.rechercherPartie(idPartie);
		Plateau plateau = gestionPlateau.rechercherPlateau(idPartie, joueur.getId());
		DonneesDUnePartie donneesDeLaPartie = donneesDesParties.get(partie.getId());
		
		Timer timer = new Timer();
		TimerTask timertask = new TimerTask() {
			
			@Override
			public void run() {
				Partie partie = gestionPartie.rechercherPartie(idPartie);
				for(Plateau p : gestionPartie.listerPlateauxEnJeu(partie)){
					if(!donneesDesParties.get(idPartie).getJoueurs().contains(p.getJoueur().getId())){
						gestionPartie.suspendreJoueur(partie, p);
					}
				}
			}
		};
		if(partie.getEtat() == Partie.Etat.FINI){
		    //Il faut forward vers la page de fin de partie
		}
		
		if(donneesDeLaPartie.getJoueursNumTours().get(joueur.getId()) < donneesDesParties.get(partie.getId()).getTour()) {
			donneesDeLaPartie.getJoueursNumTours().put(joueur.getId(), donneesDesParties.get(partie.getId()).getJoueursNumTours().get(joueur.getId())+1);
		}
		
		if(request.getParameter("numeroJeton")!=null){
			int numeroJeton = Integer.parseInt(request.getParameter("numeroJeton").replace("jeton_", "").trim());
			int idCase = Integer.parseInt(request.getParameter("idCase").replace("case_", "").trim());
			// Verfie si on est dans la phase 1 ou 2
			if(gestionPlateau.listerJetonsEnMain(plateau).size() == 0){
			    Case caseSource = gestionPlateau.recupererLaCaseContentantLeJeton(plateau, numeroJeton);
			    gestionPlateau.deplacerJeton(plateau, caseSource, gestionCase.rechercherCase(idCase));
			}else{
			    gestionPlateau.placerJeton(plateau, gestionJeton.rechercherJeton(numeroJeton), gestionCase.rechercherCase(idCase));
			}
			donneesDeLaPartie.getJoueurs().add(joueur.getId());
			if(donneesDeLaPartie.getJoueurs().size()==gestionPartie.listerPlateauxEnJeu(partie).size()){
				if(donneesDeLaPartie.getTimer()!=null) {
					donneesDeLaPartie.getTimer().cancel();
				}
				// Verifie les scores à la fin de chaque tour pour voir si un joueur à gagner
				if(gestionPlateau.listerJetonsEnMain(plateau).size() == 0){
				    List<Plateau> listePlateau = gestionPartie.listerPlateauxEnJeu(partie);
				    for (Plateau p : listePlateau){
					if(gestionPlateau.calculerScore(p) == 12){
					    gestionPartie.finirPartie(partie);
					}
				    }
				}
				
				gestionPartie.lancerDe(partie);
				partie = gestionPartie.rechercherPartie(idPartie);
				if(partie.getIndiceTirage()!=12){
					gestionPartie.piocherJeton(partie);
					partie = gestionPartie.rechercherPartie(idPartie);
				}
				donneesDeLaPartie.getJoueurs().clear();
				//timer.schedule(timertask,Util.TEMPS_INACTIVITE);
				donneesDeLaPartie.setTimer(timer);
				donneesDeLaPartie.setTour(donneesDesParties.get(partie.getId()).getTour()+1);
				donneesDeLaPartie.setResultatDe(partie.getResultatDe());
			}
		}
		
		plateau = gestionPlateau.rechercherPlateau(idPartie, joueur.getId());
		List<Case> cases = gestionPlateau.listerCases(plateau);
		List<Jeton> jetonsEnMain = gestionPlateau.listerJetonsEnMain(plateau);
		List<Case> casesLibres = new ArrayList();

		if(!donneesDeLaPartie.getJoueurs().contains(joueur.getId())){
			session.setAttribute("pasEncoreJoue", "true");
			casesLibres = gestionPlateau.listerCasesLibres(plateau, partie.getResultatDe());
		}
		else{
			session.setAttribute("pasEncoreJoue", "false");
		}
		session.setAttribute("cases", cases);
		session.setAttribute("jetonsEnMain", jetonsEnMain);
		session.setAttribute("casesLibres", casesLibres);
		if(donneesDeLaPartie.getTour() < 12) {
			session.setAttribute("phase2", "false");
		}else{
			session.setAttribute("phase2", "true");
		}
		int scorePerso = gestionPlateau.calculerScore(plateau);
		session.setAttribute("scorePerso", scorePerso);
		request.setAttribute("title-html", "Partie");
		
		getServletContext().getNamedDispatcher("plateau.html").forward(request, response);
	}
}
