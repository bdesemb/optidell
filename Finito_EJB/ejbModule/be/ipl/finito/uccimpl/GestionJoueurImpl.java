package be.ipl.finito.uccimpl;

import java.util.List;

import be.ipl.finito.dao.JoueurDao;
import be.ipl.finito.domaine.Joueur;
import be.ipl.finito.ucc.GestionJoueur;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class GestionJoueurImpl implements GestionJoueur {

	@EJB
	private JoueurDao joueurDao;

	@Override
	public Joueur inscription(String nom, String prenom, String mail,
			String pseudo, String motDePasse) {
		Joueur joueur = new Joueur(prenom, nom, mail, motDePasse, pseudo);
		joueurDao.enregistrer(joueur);
		return joueur;
	}

	@Override
	public Joueur connexion(String pseudo, String motDePasse) {
		Joueur joueur = joueurDao.rechercherJoueurViaPseudo(pseudo);
		if(joueur == null || !joueur.getMotDePasse().equals(motDePasse))
			return null;	
		return joueur;
	}

	@Override 
	public List<Joueur> listeJoueur() {
		return joueurDao.lister();
	}

	@Override
	public Joueur rechercheJoueurViaPseudo(String pseudo) {
		return joueurDao.rechercherJoueurViaPseudo(pseudo);
	}

}
