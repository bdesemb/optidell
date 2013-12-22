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
		// On r�cup�re les diff�rentes variables n�cessaires
		ServletContext context = getServletContext();	
		final HashMap<Integer, DonneesDUnePartie> donneesDesParties =  (HashMap<Integer, DonneesDUnePartie>) context.getAttribute("donneesDesParties");
		HttpSession session = request.getSession(false);
		Joueur joueur = (Joueur) session.getAttribute("joueur");
		final int idPartie = (Integer) session.getAttribute("id_partie");
		Partie partie = gestionPartie.rechercherPartie(idPartie);
		Plateau plateau = gestionPlateau.rechercherPlateau(idPartie, joueur.getId());
		final DonneesDUnePartie donneesDeLaPartie = donneesDesParties.get(partie.getId());
		int phase = (donneesDeLaPartie.getTour()>=12?2:1);
		Timer timer = new Timer();
		TimerTask timertask = new TimerTask() {
			public void run() {
				Partie partie = gestionPartie.rechercherPartie(idPartie);
				for(Plateau p : gestionPartie.listerPlateauxEnJeu(partie)){
					// Si le joueur n'est pas dans le set des joueurs ayant jou�s, on le suspend et on suspend la partie
					if(!donneesDesParties.get(idPartie).getJoueursAyantJoues().contains(p.getJoueur().getId())){
						gestionPartie.suspendreJoueur(partie, p);
						donneesDesParties.get(idPartie).setEtat("SUSPENDU");
					}
				}
			}
		};
		
		/* On met � jour le tour du joueur qui passe (utilis� pour que le joueur vienne chercher sa nouvelle main lorsqu'il y a un nouveau tour */
		if(donneesDeLaPartie.getJoueursNumTours().get(joueur.getId()) < donneesDesParties.get(partie.getId()).getTour()) {
			donneesDeLaPartie.getJoueursNumTours().put(joueur.getId(), donneesDesParties.get(partie.getId()).getJoueursNumTours().get(joueur.getId())+1);
		}
		
		// #### SI LE JOUEUR A JOUE #### Il a donc envoy� un num�ro de jeton en POST
		if(request.getParameter("numeroJeton")!=null){
			int numeroJeton = Integer.parseInt(request.getParameter("numeroJeton").replace("jeton_", "").trim());
			int idCase = Integer.parseInt(request.getParameter("idCase").replace("case_", "").trim());
			// Si on est en phase 1 ou en phase 2, on place ou on d�place
			if(phase==2){
			    Case caseSource = gestionPlateau.recupererLaCaseContentantLeJeton(plateau, numeroJeton);
			    gestionPlateau.deplacerJeton(plateau, caseSource, gestionCase.rechercherCase(idCase));
			}else{
			    gestionPlateau.placerJeton(plateau, gestionJeton.rechercherJeton(numeroJeton), gestionCase.rechercherCase(idCase));
			}
			// On enregistre que le joueur a jou� � ce tour-ci
			donneesDeLaPartie.getJoueursAyantJoues().add(joueur.getId());
			
			// #### SI NOUVEAU TOUR #### C'est � dire quand tous les joueurs sont dans le set "joueurJoues"
			if(donneesDeLaPartie.getJoueursAyantJoues().size()==gestionPartie.listerPlateauxEnJeu(partie).size()){
				// On annule l'ancien timer
				if(donneesDeLaPartie.getTimer()!=null) {
					donneesDeLaPartie.getTimer().cancel();
				}
				
				// On v�rifie les scores pour voir si quelqu'un a gagn� (apr�s le tour 12, donc quand tous les jetons ont �t� plac�s
				if(phase==2){
				    List<Plateau> listePlateau = gestionPartie.listerPlateauxEnJeu(partie);
				    for (Plateau p : listePlateau){
				    	if(gestionPlateau.calculerScore(p) == 12){
				    		gestionPartie.finirPartie(partie);
							donneesDeLaPartie.setEtat("FINI");
						}
				    }
				}
				
				// On lance le d�
				gestionPartie.lancerDe(partie);
				partie = gestionPartie.rechercherPartie(idPartie);
				// On pioche un jeton si on est en phase 1
				if(partie.getIndiceTirage()!=12){
					gestionPartie.piocherJeton(partie);
					partie = gestionPartie.rechercherPartie(idPartie);
				}
				// On vide le set des joueurs ayant jou�s
				donneesDeLaPartie.getJoueursAyantJoues().clear();
				timer.schedule(timertask,Util.TEMPS_INACTIVITE*1000); // On relance le timer
				donneesDeLaPartie.setTimer(timer); // On l'enregistre
				donneesDeLaPartie.setTour(donneesDesParties.get(partie.getId()).getTour()+1);
				donneesDeLaPartie.setResultatDe(partie.getResultatDe());
			}
		} // #### FIN DE SI LE JOUEUR A JOUE ####
		
		// On va chercher les donn�es � afficher pour le plateau, on recharge le plateau car il y a pu y avoir un mouvement
		plateau = gestionPlateau.rechercherPlateau(idPartie, joueur.getId());
		List<Case> cases = gestionPlateau.listerCases(plateau);
		List<Jeton> jetonsEnMain = gestionPlateau.listerJetonsEnMain(plateau);
		List<Case> casesLibres = new ArrayList();
		// On indique si il a d�j� jou� ou non (pour limiter � un placement par tour), on �vite d'afficher les cases libres si il a d�j� jou�
		if(!donneesDeLaPartie.getJoueursAyantJoues().contains(joueur.getId())){
			session.setAttribute("pasEncoreJoue", "true");
			casesLibres = gestionPlateau.listerCasesLibres(plateau, partie.getResultatDe());
		}
		else{
			session.setAttribute("pasEncoreJoue", "false");
		}
		// On enregistre toutes les donn�es relatives � un seul plateau dans la session
		session.setAttribute("cases", cases);
		session.setAttribute("jetonsEnMain", jetonsEnMain);
		session.setAttribute("casesLibres", casesLibres);
		// Si le joueur est suspendu
		System.out.println("Suspendu ?" +plateau.isSuspendu());
		session.setAttribute("suspendu", (plateau.isSuspendu()?"OUI":"NON"));
		if(phase==1) {
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
