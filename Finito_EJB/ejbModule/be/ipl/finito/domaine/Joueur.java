package be.ipl.finito.domaine;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entité sérialisable de joueur
 * 
 * @author Deconinck Guillaume, Marinx Denis, De Beule Anthony, Desemberg Benoit
 */
@Entity
@Table(schema = "FINITO", name = "JOUEURS")
public class Joueur implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String prenom;

	@Column(nullable = false)
	private String nom;

	public List<Plateau> getPlateaux() {
		return plateaux;
	}

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private String motDePasse;

	@Column(nullable = false, unique = true)
	private String login;

	@OneToMany(mappedBy = "joueur")
	private List<Plateau> plateaux;

	/**
	 * Constructeur vide de Joueur utile pour EJB
	 */
	protected Joueur() {
		super();
	}

	/**
	 * Constructeur de Joueur
	 * 
	 * @param prenom
	 * @param nom
	 * @param email
	 * @param motDePasse
	 * @param login
	 */
	public Joueur(String prenom, String nom, String email, String motDePasse, String login) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.email = email;
		this.motDePasse = motDePasse;
		this.login = login;
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
	 * Getter de prenom
	 * 
	 * @return prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * Getter de nom
	 * 
	 * @return nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Getter de email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Getter du mot de passe
	 * 
	 * @return le mot de passe
	 */
	public String getMotDePasse() {
		return motDePasse;
	}

	/**
	 * Getter de login
	 * 
	 * @return login
	 */
	public String getLogin() {
		return login;
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
		Joueur other = (Joueur) obj;
		if (id != other.id)
			return false;
		return true;
	}
}
