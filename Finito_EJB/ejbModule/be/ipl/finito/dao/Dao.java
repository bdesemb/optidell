package be.ipl.finito.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Interface de Dao Serialisable
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 * 
 * @param <K>
 *            Classe appliquee par les classes d'impl�mentation
 * @param <E>
 *            Classe appliquee par les classes d'impl�mentation
 */
public interface Dao<K, E> extends Serializable {
	/**
	 * Recherche une entit�
	 * 
	 * @param id
	 *            Id de l'entit�
	 * @return Entite
	 */
	E rechercher(K id);

	/**
	 * Enregistre une entit�
	 * 
	 * @param entit�
	 * @return Entite enregistr�
	 */
	E enregistrer(E entit�);

	/**
	 * Met � jour une entit�
	 * 
	 * @param entit�
	 * @return Entite mise � jour
	 */
	E mettreAJour(E entit�);

	/**
	 * Recharge une entit�
	 * 
	 * @param id
	 * @return Entite recharg�e
	 */
	E recharger(K id);

	/**
	 * Supprime une entit�
	 * 
	 * @param id
	 */
	void supprimer(K id);

	/**
	 * Retourne une liste d'entit�
	 * 
	 * @return la liste d'entit�
	 */
	List<E> lister();
}
