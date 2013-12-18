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
	 * Rechercher une entit�
	 * 
	 * @param id
	 *            Id de l'entit�
	 * @return Entite
	 */
	E rechercher(K id);

	/**
	 * Enregistrer une entit�
	 * 
	 * @param entit�
	 * @return Entite enregistr�
	 */
	E enregistrer(E entit�);

	/**
	 * Mettre � jour une entit�
	 * 
	 * @param entit�
	 * @return Entite mise � jour
	 */
	E mettreAJour(E entit�);

	/**
	 * Recharger une entit�
	 * 
	 * @param id
	 * @return Entite recharg�e
	 */
	E recharger(K id);

	/**
	 * Supprimer une entit�
	 * 
	 * @param id
	 */
	void supprimer(K id);

	/**
	 * Retourner une liste d'entit�
	 * 
	 * @return la liste d'entit�
	 */
	List<E> lister();
}
