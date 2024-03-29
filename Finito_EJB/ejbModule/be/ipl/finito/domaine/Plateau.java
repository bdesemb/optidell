package be.ipl.finito.domaine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entit� s�rialisable de plateau
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@Entity
@Table(schema = "FINITO", name = "PLATEAUX")
public class Plateau implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "PARTIE_ID")
	private Partie partie;

	@ManyToOne
	@JoinColumn(name = "JOUEUR_ID")
	private Joueur joueur;

	@OneToMany( cascade = { CascadeType.ALL })
	@JoinColumn(name = "PLATEAU_ID")
	@OrderBy("id")
	private List<Case> cases = new ArrayList<Case>();

	@Transient
	private List<Jeton> jetonsEnMain = new ArrayList<Jeton>();
	
	private boolean suspendu;

	/**
	 * Constructeur vide de Plateau utile pour EJB
	 */
	protected Plateau() {
		super();
	}
	/**
	 * Constructeur de Plateau
	 * @param joueur joueur ayant cr��e la partie
	 * @param partie
	 */
	public Plateau(Joueur joueur, Partie partie){
		this.joueur = joueur;
		this.partie = partie;
		this.suspendu = false;
	}

	/**
	 * Getter de Id
	 * 
	 * @return l'id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter de partie du plateau
	 * 
	 * @return la partie
	 */
	public Partie getPartie() {
		return partie;
	}
	
	/**
	 * Getter du joueur � qui appartient du plateau
	 * 
	 * @return le joueur
	 */
	public Joueur getJoueur() {
		return joueur;
	}

	/**
	 * Getter de la liste des cases
	 * 
	 * @return la liste des cases
	 */
	public List<Case> getCases() {
		return cases;
	}

	/**
	 * Getter des jetons en main
	 * 
	 * @return les jetons en main
	 */
	public List<Jeton> getJetonsEnMain() {
		return jetonsEnMain;
	}
	/**
	 * getter de suspendu
	 * @return la valeur de suspendu
	 */
	public boolean isSuspendu() {
		return suspendu;
	}
	/**
	 * Setter de suspendu 
	 * @param suspendu
	 */
	public void setSuspendu(boolean suspendu) {
		this.suspendu = suspendu;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plateau other = (Plateau) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
