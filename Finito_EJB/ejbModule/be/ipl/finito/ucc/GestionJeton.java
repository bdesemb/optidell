package be.ipl.finito.ucc;

import javax.ejb.Remote;

import be.ipl.finito.domaine.Jeton;

/**
 * Cas d'utilisation pour la gestion des cases
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@Remote
public interface GestionJeton {
	/**
	 * Recherche le jeton dont on connait son numéro
	 * 
	 * @param numero
	 * @return le jeton
	 */
	Jeton rechercherJeton(int numero);

}