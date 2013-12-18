package be.ipl.finito.domaine;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Entité sérialisable du case
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CASES", schema = "FINITO")
public class Case implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int numero;

	@OneToOne(cascade = { CascadeType.ALL })
	@PrimaryKeyJoinColumn
	private Jeton jeton;

	/**
	 * Constructeur vide de Case utile pour EJB
	 */
	protected Case() {
	}

	/**
	 * Constructeur de Case
	 * 
	 * @param numero
	 */
	public Case(int numero) {
		this.numero = numero;
	}

	/**
	 * Getter de jeton
	 * 
	 * @return le jeton
	 */
	public final Jeton getJeton() {
		return jeton;
	}

	/**
	 * Setter de jeton
	 * 
	 * @param jeton
	 */
	public final void setJeton(Jeton jeton) {
		this.jeton = jeton;
	}

	/**
	 * Getter de id
	 * 
	 * @return l'id de Case
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter de numéro de case
	 * 
	 * @return le numéro
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
		Case other = (Case) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
