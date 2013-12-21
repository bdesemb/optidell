package be.ipl.finito.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface de Dao Serialisable
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 * 
 * @param <K>
 *            Classe appliquee par les classes d'implémentation
 * @param <E>
 *            Classe appliquee par les classes d'implémentation
 */
public interface Dao<K, E> extends Serializable {
	/**
	 * Recherche une entité
	 * 
	 * @param id
	 *            Id de l'entité
	 * @return Entite
	 */
	E rechercher(K id);

	/**
	 * Enregistre une entité
	 * 
	 * @param entité
	 * @return Entite enregistré
	 */
	E enregistrer(E entité);

	/**
	 * Met à jour une entité
	 * 
	 * @param entité
	 * @return Entite mise à jour
	 */
	E mettreAJour(E entité);

	/**
	 * Recharge une entité
	 * 
	 * @param id
	 * @return Entite rechargée
	 */
	E recharger(K id);

	/**
	 * Supprime une entité
	 * 
	 * @param id
	 */
	void supprimer(K id);

	/**
	 * Retourne une liste d'entité
	 * 
	 * @return la liste d'entité
	 */
	List<E> lister();
}
