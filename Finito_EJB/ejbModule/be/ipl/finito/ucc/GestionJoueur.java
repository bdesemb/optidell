package be.ipl.finito.ucc;

import java.util.List;

import be.ipl.finito.domaine.Joueur;
import javax.ejb.Remote;

@Remote
public interface GestionJoueur {
    Joueur inscription(String nom, String prenom, String mail, String pseudo, String motDePasse);
    Joueur connexion(String pseudo, String motDePasse);
    List<Joueur>listeJoueur();
    Joueur rechercheJoueurViaPseudo(String pseudo);
}
