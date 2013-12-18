package be.ipl.finito.domaine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entité sérialisable de jeton
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JETONS", schema = "FINITO")
public class Jeton implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int numero;

	/**
	 * Constructeur vide de Jeton utile pour EJB
	 */
	protected Jeton() {

	}

	/**
	 * Constructeur de jeton
	 * 
	 * @param numero
	 */
	public Jeton(int numero) {
		this.numero = numero;
	}

	/**
	 * Getter de l'id
	 * 
	 * @return l'id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter de numéro
	 * 
	 * @return numero
	 */
	public int getNumero() {
		return numero;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jeton other = (Jeton) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
