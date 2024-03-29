package be.ipl.finito.daoimpl;

import be.ipl.finito.dao.Dao;

import java.lang.reflect.ParameterizedType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

@SuppressWarnings("serial")
/**
 * Impl�mentation du Dao
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 * 
 * @param <K>
 *            Classe appliquee par les classes d'impl�mentation
 * @param <E>
 *            Classe appliquee par les classes d'impl�mentation
 */
public abstract class DaoImpl<K, E> implements Dao<K, E> {
	private Class<E> entityClass;

	@PersistenceContext(unitName="finito")
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public DaoImpl() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public E rechercher(K id) {
		return (E) entityManager.find(entityClass, id);
	}

	public E enregistrer(E entit�) {
		entityManager.persist(entit�);
		return entit�;
	}

	public E mettreAJour(E entit�) {
		return entityManager.merge(entit�);		
	}

	public E recharger(K id) {
		E entit� = rechercher(id);
		entityManager.refresh(entit�);
		return entit�;
	}

	public void supprimer(K id) {
		E entit� = rechercher(id);
		entityManager.remove(entit�);
	}

	public List<E> lister() {
		return liste("select x from " + entityClass.getName() + " x");
	}

	protected List<E> liste(String queryString, Object... params) {
		List<E> entit�s = null;
		TypedQuery<E> query = entityManager.createQuery(queryString, entityClass);
		int i = 0, j = 1;
		while(i < params.length) {
			if (params[i] instanceof Date ) {
				query.setParameter(j, (Date)params[i], (TemporalType) params[i+1]);
				i+=2; 
			} else if (params[i] instanceof Calendar) {
				query.setParameter(j, (Calendar)params[i], (TemporalType) params[i+1]);
				i+=2; 
			} else {
				query.setParameter(j, params[i]);
				i++; 
			}
			j++;
		}
		entit�s = query.getResultList();
		return entit�s;
	}

	protected E recherche(String queryString, Object... params) {
		try {
			TypedQuery<E> query = entityManager.createQuery(queryString, entityClass);
			int i = 0, j = 1;
			while(i < params.length) {
				if (params[i] instanceof Date ) {
					query.setParameter(j, (Date)params[i], (TemporalType) params[i+1]);
					i+=2; 
				} else if (params[i] instanceof Calendar) {
					query.setParameter(j, (Calendar)params[i], (TemporalType) params[i+1]);
					i+=2; 
				} else {
					query.setParameter(j, params[i]);
					i++; 
				}
				j++;
			}
			return (E) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			return null; // throw new InternalError();
		}
	}
}
