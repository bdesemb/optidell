package be.ipl.finito.ucc;

import javax.ejb.Remote;

import be.ipl.finito.domaine.Case;
import be.ipl.finito.domaine.Jeton;

/**
 * Cas d'utilisation pour la gestion des cases
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@Remote
public interface GestionCase {
	/**
	 * Crée une nouvelle case
	 * 
	 * @param numero
	 * @return
	 */
	Case creerCase(int numero);

	/**
	 * Place un jeton dans une case
	 * 
	 * @param caseLue
	 *            case sur lequel on veut placer le jeton
	 * @param jeton
	 * @return si l'ajout du jeton dans la case s'est bien effectué ou non
	 */
	boolean placerJeton(Case caseLue, Jeton jeton);

	/**
	 * retire le jeton d'une case
	 * 
	 * @param caseLue
	 *            la case où l'on souhaite retirer le jeton
	 * @param jeton
	 * @return si le retrait du jeton dans la case s'est bien effectué ou non
	 */
	boolean retirerJeton(Case caseLue, Jeton jeton);

	Case rechercherCasePourId(int id);

}
